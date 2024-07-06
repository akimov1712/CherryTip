package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.call.body
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.data.source.network.service.UserApi
import ru.topbun.cherry_tip.domain.entity.user.AccountInfoEntity
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository
import ru.topbun.cherry_tip.utills.AccountInfoNotComplete
import ru.topbun.cherry_tip.utills.FailedExtractToken
import ru.topbun.cherry_tip.utills.codeResultWrapper
import ru.topbun.cherry_tip.utills.exceptionWrapper

class UserRepositoryImpl(
    private val api: UserApi,
    private val dataStore: DataStore<Preferences>
): UserRepository {

    private suspend fun getToken(): String {
        val token = dataStore.data
            .map { it[AppSettings.KEY_TOKEN] }
            .firstOrNull()

        return token ?: throw FailedExtractToken()
    }

    override suspend fun createProfile(profile: ProfileEntity): Unit = exceptionWrapper{
        api.createProfile(profile = profile.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun updateProfile(profile: ProfileEntity): Unit = exceptionWrapper {
        api.updateProfile(profile = profile.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun createGoal(goal: GoalEntity): Unit = exceptionWrapper {
        api.createGoal(goal = goal.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun updateGoal(goal: GoalEntity): Unit = exceptionWrapper {
        api.updateGoal(goal = goal.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun createUnits(units: UnitsEntity): Unit = exceptionWrapper {
        api.createUnits(units = units.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun updateUnits(units: UnitsEntity): Unit = exceptionWrapper {
        api.updateUnits(units = units.toDto(), token = getToken()).codeResultWrapper()
    }

    override suspend fun getAccountInfo(): AccountInfoEntity = exceptionWrapper {
        api.getAccountInfo(getToken()).body()
    }

    override suspend fun tokenIsValid() {
        if (getToken().isBlank()) throw FailedExtractToken()
    }

    override suspend fun checkAccountInfoComplete() {
        getAccountInfo().apply {
            if (goal == null || units == null || profile == null) throw AccountInfoNotComplete()
        }
    }
}