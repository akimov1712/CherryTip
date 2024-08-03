package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.dto.challenge.ChallengeStatusDto
import ru.topbun.cherry_tip.data.source.network.token
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus

class ChallengeApi(
    private val api: ApiFactory
) {

    suspend fun getChallenge(token: String, status: ChallengeStatus) = api.client.get("/v1/challenge/search") {
        token(token)
        if (status != ChallengeStatus.All){
            val status = when(status){
                ChallengeStatus.Active -> ChallengeStatusDto.Started
                ChallengeStatus.Finished -> ChallengeStatusDto.Finished
                else -> return@get
            }
            parameter("status", status)
        }
    }

    suspend fun startChallenge(token: String, id: Int) = api.client.post("/v1/challenge/$id/start"){
        token(token)
    }

    suspend fun cancelChallenge(token: String, id: Int) = api.client.post("/v1/challenge/$id/cancel"){
        token(token)
    }

    suspend fun getUserChallengeById(token: String, id: Int) = api.client.get("/v1/challenge/$id/status"){
        token(token)
    }
}