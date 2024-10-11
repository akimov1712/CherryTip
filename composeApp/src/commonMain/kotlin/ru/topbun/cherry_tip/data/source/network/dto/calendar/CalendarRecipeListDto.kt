package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarRecipeListDto(
    @SerialName("id") val id: Int,
    @SerialName("recipe") val recipe: CalendarRecipeDto
)
