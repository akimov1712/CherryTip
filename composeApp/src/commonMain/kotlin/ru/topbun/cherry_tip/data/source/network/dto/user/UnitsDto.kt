package ru.topbun.cherry_tip.data.source.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnitsDto(
    @SerialName("weight") val weight: Int,
    @SerialName("height") val height: Int,
    @SerialName("targetWeight") val targetWeight: Int,
    @SerialName("bloodGlucose") val bloodGlucose: Int?,
)
