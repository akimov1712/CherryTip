package ru.topbun.cherry_tip.data.repository

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.data.source.local.dataStore.entity.glass.GlassStoreModel
import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity
import ru.topbun.cherry_tip.domain.repository.GlassRepository
import ru.topbun.cherry_tip.domain.repository.UserRepository
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.Const
import ru.topbun.cherry_tip.utills.now

class GlassRepositoryImpl(
    private val userRepository: UserRepository,
    private val dataStore: Settings
): GlassRepository {

    private val glass = dataStore.data.
        map { it[AppSettings.KEY_GLASS] }


    override val countGlassFlow = flow {
        val weight = userRepository.getAccountInfo().units?.weight ?: throw AccountInfoNotCompleteException()
        val countNeededGlass = weight * Const.ML_PER_KG / Const.ML_GLASS
        checkReset(countNeededGlass)
        glass.collect{ jsonGlass ->
            jsonGlass?.let {
                val result = Json.decodeFromString<GlassStoreModel>(it).countDrinkGlass
                emit(GlassEntity(result, countNeededGlass))
            }
        }
    }


    override suspend fun addDrinkGlass() {
        val glassJson = glass.firstOrNull()
        glassJson?.let {
            val glass = Json.decodeFromString<GlassStoreModel>(it)
            dataStore.edit { settings ->
                settings[AppSettings.KEY_GLASS] =
                    Json.encodeToString(glass.copy(countDrinkGlass = glass.countDrinkGlass + 1))
            }
        }
    }

    private suspend fun checkReset(countNeededGlass: Int) {
        val glassJson = glass.firstOrNull()
        glassJson?.let {
            val glass = Json.decodeFromString<GlassStoreModel>(it)
            if (glass.day != LocalDate.now().toEpochDays()) resetGlass( countNeededGlass)
        } ?: resetGlass(countNeededGlass)
    }

    private suspend fun resetGlass(countNeededGlass: Int){
        val glass = GlassStoreModel(0, countNeededGlass, LocalDate.now().toEpochDays())
        dataStore.edit {
            it[AppSettings.KEY_GLASS] = Json.encodeToString(glass)
        }
    }

}