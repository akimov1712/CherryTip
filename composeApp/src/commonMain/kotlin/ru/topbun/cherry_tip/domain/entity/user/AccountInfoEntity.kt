package ru.topbun.cherry_tip.domain.entity.user

import io.ktor.util.date.GMTDate

data class AccountInfoEntity(
    val id: String,
    val createdAt: GMTDate,
    val updatedAt: GMTDate,
    val email: String,
    val login: String,
    val role: String,
    val passwordHash: String,
    val profile: ProfileEntity?,
    val units: UnitsEntity?,
    val goal: GoalEntity?
)
