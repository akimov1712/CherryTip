package ru.topbun.cherry_tip.data.source.network.dto.challenge

import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.challenge.Difficulty

@Serializable
data class ChallengeDto(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val color: String,
    val durationDays: Int,
    val difficulty: Difficulty,
    val tips: List<String>
)
