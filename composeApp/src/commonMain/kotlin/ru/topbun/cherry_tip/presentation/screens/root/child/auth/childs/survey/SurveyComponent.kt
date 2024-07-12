package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey

import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType

interface SurveyComponent {

    val state: StateFlow<SurveyStore.State>

    fun changeName(name: String)
    fun changeGoal(goalType: GoalType)
    fun changeGender(gender: Gender)
    fun changeAge(age: GMTDate)
    fun changeHeight(height: Int)
    fun changeWeight(weight: Int)
    fun changeTargetWeight(targetWeight: Int)
    fun changeActive(active: ActiveType)
    fun sendSurvey()

    fun nextFragment()
    fun previousFragment()

}