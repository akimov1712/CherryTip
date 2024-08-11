package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units

import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender

interface UnitsComponent {

    val state: StateFlow<UnitsStore.State>

    fun saveData()
    fun clickBack()

    fun changeWeight(weight: Int)
    fun changeTargetWeight(targetWeight: Int)
    fun changeHeight(height: Int)
    fun changeBloodGlucose(bloodGlucose: Int)

}