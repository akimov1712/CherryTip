package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.goal_active
import cherrytip.composeapp.generated.resources.goal_calorie
import cherrytip.composeapp.generated.resources.goal_goal
import org.jetbrains.compose.resources.StringResource

enum class GoalItems(
    val stringRes: StringResource
) {

    Goal(Res.string.goal_goal),
    Active(Res.string.goal_active),
    Calorie(Res.string.goal_calorie),

}