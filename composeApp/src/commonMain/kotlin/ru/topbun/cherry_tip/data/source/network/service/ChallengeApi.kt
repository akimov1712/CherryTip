package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.get
import io.ktor.client.request.setBody
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.token
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus

class ChallengeApi(
    private val api: ApiFactory
) {

    suspend fun getChallenge(token: String, status: ChallengeStatus) = api.client.get("/v1/challenge/search") {
        token(token)
        if (status == ChallengeStatus.Active) setBody(ChallengeStatusDto.Started)
    }

    private enum class ChallengeStatusDto{
        Started, Canceled, Finished;
    }
}