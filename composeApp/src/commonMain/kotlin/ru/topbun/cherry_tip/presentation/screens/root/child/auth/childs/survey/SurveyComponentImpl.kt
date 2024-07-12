package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.utills.componentScope

class SurveyComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SurveyStoreFactory,
    private val onSendSurvey: () -> Unit,
) : SurveyComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.store }
    override val state: StateFlow<SurveyStore.State>
        get() = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    SurveyStore.Label.SendSurvey -> onSendSurvey()
                }
            }
        }
    }

    override fun changeName(name: String) = store.accept(Intent.ChangeName(name))
    override fun changeGoal(goalType: GoalType) = store.accept(Intent.ChangeGoal(goalType))
    override fun changeGender(gender: Gender) = store.accept(Intent.ChangeGender(gender))
    override fun changeAge(age: GMTDate) = store.accept(Intent.ChangeAge(age))
    override fun changeHeight(height: Int) = store.accept(Intent.ChangeHeight(height))
    override fun changeWeight(weight: Int) = store.accept(Intent.ChangeWeight(weight))
    override fun changeTargetWeight(targetWeight: Int) = store.accept(Intent.ChangeTargetWeight(targetWeight))
    override fun changeActive(active: ActiveType) = store.accept(Intent.ChangeActive(active))
    override fun sendSurvey() = store.accept(Intent.SendSurvey)
    override fun nextFragment() = store.accept(Intent.NextFragment)
    override fun previousFragment() = store.accept(Intent.PreviousFragment)
}