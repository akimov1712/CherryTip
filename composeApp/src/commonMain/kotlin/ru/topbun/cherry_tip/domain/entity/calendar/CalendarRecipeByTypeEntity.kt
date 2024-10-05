package ru.topbun.cherry_tip.domain.entity.calendar

data class CalendarRecipeByTypeEntity(
    val id: Int,
    val category: CalendarType,
    val dayId: Int,
    val recipes: List<CalendarRecipeEntity>
)