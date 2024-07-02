package ru.topbun.cherry_tip.data.source.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("login") val username: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)
