package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.gain_weight
import cherrytip.composeapp.generated.resources.ic_apple_fruit
import cherrytip.composeapp.generated.resources.ic_gym
import cherrytip.composeapp.generated.resources.ic_scale
import cherrytip.composeapp.generated.resources.lose_weight
import cherrytip.composeapp.generated.resources.stay_healthy
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class GoalObjects(
    val goalType: GoalType,
    val textRes: StringResource,
    val iconRes: DrawableResource,
) {

    Lose(GoalType.Lose, Res.string.lose_weight, Res.drawable.ic_scale),
    Stay(GoalType.Stay, Res.string.stay_healthy, Res.drawable.ic_apple_fruit),
    Gain(GoalType.Gain, Res.string.gain_weight, Res.drawable.ic_gym),

}