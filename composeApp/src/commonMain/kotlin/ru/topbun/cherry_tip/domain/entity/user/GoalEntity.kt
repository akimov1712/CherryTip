package ru.topbun.cherry_tip.domain.entity.user

import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.goal.GoalType

data class GoalEntity(
    val active: ActiveType,
    val goalType: GoalType
)
