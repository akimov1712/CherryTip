package ru.topbun.cherry_tip.domain.entity.challenge

import io.ktor.util.date.GMTDate

data class UserChallengeEntity(
    val id: Int,
    val startDate: GMTDate,
    val status: ChallengeStatus
)