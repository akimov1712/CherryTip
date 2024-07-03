package ru.topbun.cherry_tip.data.source.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoDto(
    @SerialName("id") val id: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("email") val email: String,
    @SerialName("login") val login: String,
    @SerialName("role") val role: String,
    @SerialName("passwordHash") val passwordHash: String,
    @SerialName("profile") val profile: ProfileDto?,
    @SerialName("units") val units: UnitsDto?,
    @SerialName("goal") val goal: GoalDto?
)
