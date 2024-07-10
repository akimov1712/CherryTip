package ru.topbun.cherry_tip.data.source.network.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.goal.GoalType

@Serializable
data class GoalDto(
    @SerialName("activity") val active: ActiveType,
    @SerialName("type") val goalType: GoalType,
    @SerialName("calorieGoal") val calorieGoal: Int? = null,
)
