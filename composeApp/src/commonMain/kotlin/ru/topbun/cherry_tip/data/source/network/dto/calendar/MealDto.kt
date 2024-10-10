package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

@Serializable
data class MealDto(
    @SerialName("id") val id: Int,
    @SerialName("category") val calendarType: CalendarType,
    @SerialName("dayId") val dayId: Int,
    @SerialName("recipes") val recipes: List<CalendarRecipeListDto>
)
