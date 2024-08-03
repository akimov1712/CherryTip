package ru.topbun.cherry_tip.domain.entity.challenge

import androidx.compose.ui.graphics.Color

data class ChallengeEntity(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val color: Color,
    val durationDays: Int,
    val difficulty: Difficulty,
    val tips: List<String>,
    val userChallenge: UserChallengeEntity?
)