package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

data class CalendarTypeRecipeDto(
    @SerialName("id") val id: Int,
    @SerialName("category") val category: CalendarType,
    @SerialName("dayId") val dayId: Int,
    @SerialName("recipes") val recipes: List<CalendarRecipeDto>
)