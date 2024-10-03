package ru.topbun.cherry_tip.domain.entity.calendar

data class CalendarRecipeEntity(
    val id: Int,
    val calories: Int,
    val protein: String,
    val fat: String,
    val carbs: String,
)