package ru.topbun.cherry_tip.data.source.network.dto.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus

@Serializable
data class UserChallengeDto(
    @SerialName("id") val id: Int,
    @SerialName("startDate") val startDate: String,
    @SerialName("status") val status: ChallengeStatusDto,
)