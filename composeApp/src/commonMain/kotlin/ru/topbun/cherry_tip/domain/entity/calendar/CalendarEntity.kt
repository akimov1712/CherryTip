package ru.topbun.cherry_tip.domain.entity.calendar

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType

data class CalendarEntity(
    val id: Int,
    val date: GMTDate,
    val goal: GoalType,
    val needCalories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val breakfast: Int,
    val lunch: Int,
    val dinner: Int,
    val snack: Int,
    val userId: String,
    val recipes: List<MealEntity>
)
