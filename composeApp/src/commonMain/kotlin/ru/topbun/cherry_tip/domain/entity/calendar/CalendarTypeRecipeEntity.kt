package ru.topbun.cherry_tip.domain.entity.calendar

import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarRecipeDto

data class CalendarTypeRecipeEntity(
    val id: Int,
    val category: CalendarType,
    val dayId: Int,
    val recipes: List<CalendarRecipeEntity>
)