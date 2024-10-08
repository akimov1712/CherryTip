package ru.topbun.cherry_tip.data.repository

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import androidx.datastore.preferences.core.edit
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.mapper.toEntity
import ru.topbun.cherry_tip.data.source.local.dataStore.AppSettings
import ru.topbun.cherry_tip.data.source.local.getToken
import ru.topbun.cherry_tip.data.source.network.dto.user.AccountInfoDto
import ru.topbun.cherry_tip.data.source.network.service.UserApi
import ru.topbun.cherry_tip.domain.entity.user.AccountInfoEntity
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.FailedExtractTokenException
import ru.topbun.cherry_tip.utills.codeResultWrapper
import ru.topbun.cherry_tip.utills.exceptionWrapper

class UserRepositoryImpl(
    private val api: UserApi,
    private val dataStore: Settings
): UserRepository {

    override suspend fun createProfile(profile: ProfileEntity): Unit = exceptionWrapper{
        api.createProfile(profile = profile.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun updateProfile(profile: ProfileEntity): Unit = exceptionWrapper {
        api.updateProfile(profile = profile.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun createGoal(goal: GoalEntity): Unit = exceptionWrapper {
        api.createGoal(goal = goal.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun updateGoal(goal: GoalEntity): Unit = exceptionWrapper {
        api.updateGoal(goal = goal.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun createUnits(units: UnitsEntity): Unit = exceptionWrapper {
        api.createUnits(units = units.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun updateUnits(units: UnitsEntity): Unit = exceptionWrapper {
        api.updateUnits(units = units.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun getAccountInfo(): AccountInfoEntity = exceptionWrapper {
        val response = api.getAccountInfo(dataStore.getToken())
        if (response.status == HttpStatusCode.NotFound) throw FailedExtractTokenException()
        response.codeResultWrapper().body<AccountInfoDto>().toEntity()
    }

    override suspend fun tokenIsValid() {
        if (dataStore.getToken().isBlank()) throw FailedExtractTokenException()
    }

    override suspend fun checkAccountInfoComplete() {
        getAccountInfo().apply {
            if (listOf(goal,units, profile).contains(null)) throw AccountInfoNotCompleteException()
        }
    }

    override suspend fun logOut() {
        try {
            dataStore.edit {
                it[AppSettings.KEY_TOKEN] = ""
            }
        } catch (e: Exception){}
    }
}