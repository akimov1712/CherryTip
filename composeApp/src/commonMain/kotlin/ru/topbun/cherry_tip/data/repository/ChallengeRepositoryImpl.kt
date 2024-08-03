package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.call.body
import ru.topbun.cherry_tip.data.mapper.toEntity
import ru.topbun.cherry_tip.data.mapper.toEntityList
import ru.topbun.cherry_tip.data.source.local.getToken
import ru.topbun.cherry_tip.data.source.network.dto.challenge.ChallengeDto
import ru.topbun.cherry_tip.data.source.network.dto.challenge.UserChallengeDto
import ru.topbun.cherry_tip.data.source.network.service.ChallengeApi
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.entity.challenge.UserChallengeEntity
import ru.topbun.cherry_tip.domain.repository.ChallengeRepository
import ru.topbun.cherry_tip.utills.codeResultWrapper
import ru.topbun.cherry_tip.utills.exceptionWrapper

class ChallengeRepositoryImpl(
    private val api: ChallengeApi,
    private val dataStore: DataStore<Preferences>
): ChallengeRepository {


    override suspend fun getChallenges(status: ChallengeStatus): List<ChallengeEntity> = exceptionWrapper {
        api.getChallenge(token = dataStore.getToken(), status = status)
            .codeResultWrapper().body<List<ChallengeDto>>().toEntityList()
    }

    override suspend fun startChallenge(id: Int): UserChallengeEntity = exceptionWrapper{
        api.startChallenge(token = dataStore.getToken(), id = id)
            .codeResultWrapper().body<UserChallengeDto>().toEntity()
    }

    override suspend fun cancelChallenge(id: Int): UserChallengeEntity = exceptionWrapper{
        api.cancelChallenge(token = dataStore.getToken(), id = id)
            .codeResultWrapper().body<UserChallengeDto>().toEntity()
    }

    override suspend fun getUserChallengeById(id: Int): ChallengeEntity = exceptionWrapper{
        api.getUserChallengeById(token = dataStore.getToken(), id = id)
            .codeResultWrapper().body<ChallengeDto>().toEntity()
    }

}