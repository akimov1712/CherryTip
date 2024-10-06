package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal

import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType

interface GoalComponent {

    val state: StateFlow<GoalStore.State>

    fun saveData()
    fun clickBack()
    fun load()

    fun changeGoal(goal: GoalType)
    fun changeActive(active: ActiveType)

}