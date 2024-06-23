package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.gain_weight
import cherrytip.composeapp.generated.resources.ic_apple_fruit
import cherrytip.composeapp.generated.resources.ic_gym
import cherrytip.composeapp.generated.resources.ic_scale
import cherrytip.composeapp.generated.resources.lose_weight
import cherrytip.composeapp.generated.resources.stay_healthy
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class GoalItems(
    val goal: Goal,
    val textRes: StringResource,
    val iconRes: DrawableResource,
) {

    data object Lose: GoalItems(Goal.Lose, Res.string.lose_weight, Res.drawable.ic_scale)
    data object Stay: GoalItems(Goal.Stay, Res.string.stay_healthy, Res.drawable.ic_apple_fruit)
    data object Gain: GoalItems(Goal.Gain, Res.string.gain_weight, Res.drawable.ic_gym)

}