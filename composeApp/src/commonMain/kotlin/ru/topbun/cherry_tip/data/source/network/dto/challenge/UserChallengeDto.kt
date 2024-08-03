package ru.topbun.cherry_tip.data.source.network.dto.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserChallengeDto(
    @SerialName("id") val id: Int,
    @SerialName("startDate") val startDate: String,
    @SerialName("status") val status: ChallengeStatusDto,
    @SerialName("userId") val userId: String,
    @SerialName("challengeId") val challengeId: Int? = 0,
)