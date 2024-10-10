package ru.topbun.cherry_tip.domain.entity.calendar

data class MealEntity(
    val id: Int,
    val calendarType: CalendarType,
    val dayId: Int,
    val recipes: List<CalendarRecipeEntity>
)