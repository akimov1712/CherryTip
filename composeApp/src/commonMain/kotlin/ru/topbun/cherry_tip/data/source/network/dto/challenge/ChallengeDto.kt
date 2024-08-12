package ru.topbun.cherry_tip.data.source.network.dto.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.Difficulty

@Serializable
data class ChallengeDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("image") val image: String,
    @SerialName("color") val color: String,
    @SerialName("durationDays") val durationDays: Int,
    @SerialName("difficulty") val difficulty: Difficulty,
    @SerialName("tips") val tips: List<String>,
    @SerialName("userChallenge") val userChallenge: UserChallengeDto? = null,
    @SerialName("challengeId") val challengeId: Int? = null
)