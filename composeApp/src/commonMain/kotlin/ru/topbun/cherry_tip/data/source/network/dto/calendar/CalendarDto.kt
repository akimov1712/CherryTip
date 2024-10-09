package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType

@Serializable
data class CalendarDto(
    @SerialName("id") val id: Int,
    @SerialName("date") val date: String,
    @SerialName("goal") val goal: GoalType,
    @SerialName("needCalories") val needCalories: Int,
    @SerialName("protein") val protein: Int,
    @SerialName("carbs") val carbs: Int,
    @SerialName("fat") val fat: Int,
    @SerialName("breakfast") val breakfast: Int,
    @SerialName("lunch") val lunch: Int,
    @SerialName("dinner") val dinner: Int,
    @SerialName("snack") val snack: Int,
    @SerialName("userId") val userId: String,
    @SerialName("recipes") val recipes: List<CalendarRecipeByTypeDto>
)
