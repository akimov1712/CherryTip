package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarRecipeDto(
    @SerialName("id") val id: Int,
    @SerialName("calories") val calories: Int,
    @SerialName("protein") val protein: String,
    @SerialName("fat") val fat: String,
    @SerialName("carbs") val carbs: String,
)