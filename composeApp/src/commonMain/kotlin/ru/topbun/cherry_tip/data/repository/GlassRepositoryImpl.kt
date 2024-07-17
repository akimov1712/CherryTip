package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity
import ru.topbun.cherry_tip.domain.repository.GlassRepository
import ru.topbun.cherry_tip.domain.repository.UserRepository
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException

class GlassRepositoryImpl(
    private val userRepository: UserRepository,
    private val dataStore: DataStore<Preferences>
): GlassRepository {

    private val countGlass = dataStore.data.
        map { it[AppSettings.COUNT_GLASS_TOKEN] }


    override val countGlassFlow = flow {
        val weight = userRepository.getAccountInfo().units?.weight ?: throw AccountInfoNotCompleteException()
        val countNeededGlass = weight * 50 / 250
        countGlass.collect{
            val result = it?.toInt() ?: 0
            emit(GlassEntity(result, countNeededGlass))
        }
    }


    override suspend fun addDrinkGlass() {
        val countDrinkGlasses = countGlass.firstOrNull()?.toInt() ?: 0
        dataStore.edit {
            it[AppSettings.COUNT_GLASS_TOKEN] = (countDrinkGlasses + 1).toString()
        }
    }

}