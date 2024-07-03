package ru.topbun.cherry_tip.presentation.screens.auth.childs.survey

import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.goal.GoalType

interface SurveyComponent {

    val state: StateFlow<Nothing>

    fun changeName(name: String)
    fun changeGoal(goalType: GoalType)
    fun changeGender(gender: Gender)
    fun changeAge(age: Int)
    fun changeHeight(height: Int)
    fun changeWeight(weight: Int)
    fun changeTargetWeight(targetWeight: Int)
    fun changeActive(active: ActiveType)
    fun sendSurvey()

}