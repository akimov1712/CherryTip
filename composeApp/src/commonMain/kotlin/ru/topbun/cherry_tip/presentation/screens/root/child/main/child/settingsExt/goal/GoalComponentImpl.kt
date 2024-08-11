package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStore
import ru.topbun.cherry_tip.utills.componentScope

class GoalComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onLogOut: () -> Unit,
    private val storeFactory: GoalStoreFactory
) : GoalComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    GoalStore.Label.ClickBack -> onClickBack
                    GoalStore.Label.LogOut -> onLogOut
                }
            }
        }
    }
    override fun saveData() = store.accept(GoalStore.Intent.SaveData)
    override fun clickBack() = store.accept(GoalStore.Intent.ClickBack)
    override fun changeGoal(goal: GoalType) = store.accept(GoalStore.Intent.ChangeGoal(goal))
    override fun changeActive(active: ActiveType) = store.accept(GoalStore.Intent.ChangeActive(active))
}