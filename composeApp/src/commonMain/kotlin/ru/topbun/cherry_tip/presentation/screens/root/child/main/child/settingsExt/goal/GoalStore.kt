package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateProfileUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface GoalStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object SaveData: Intent
        data object ClickBack: Intent

        data class ChangeGoal(val goal: GoalType): Intent
        data class ChangeActive(val active: ActiveType): Intent
    }

    data class State(
        val goal: GoalType,
        val active: ActiveType,
        val goalState: GoalState
    ){

        sealed interface GoalState{
            data object Initial: GoalState
            data object Loading: GoalState
            data class Error(val text: String): GoalState
            data object Result: GoalState
        }

    }

    sealed interface Label {
        data object ClickBack: Label
        data object LogOut: Label
    }
}

class GoalStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val updateGoalUseCase: UpdateGoalUseCase
) {

    fun create(): GoalStore =
        object : GoalStore, Store<Intent, State, Label> by storeFactory.create(
            name = "GoalStore",
            initialState = State(
                goal = GoalType.Lose,
                active = ActiveType.Low,
                goalState = State.GoalState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class GoalStateError(val text: String): Action
        data object GoalStateLoading: Action
        data class GoalStateResult(
            val goal: GoalType,
            val active: ActiveType,
        ): Action

        data object LogOut: Action
    }

    private sealed interface Msg {
        data class ChangeGoal(val goal: GoalType): Msg
        data class ChangeActive(val active: ActiveType): Msg

        data class GoalStateError(val text: String): Msg
        data object GoalStateLoading: Msg
        data class GoalStateResult(
            val goal: GoalType,
            val active: ActiveType,
        ): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.GoalStateLoading)
                    val accountInfo = getAccountInfoUseCase()
                    dispatch(Action.GoalStateResult(
                        goal = accountInfo.goal?.goalType ?: GoalType.Lose,
                        active = accountInfo.goal?.active ?: ActiveType.Low
                    ))
                }){
                    dispatch(Action.GoalStateError(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.GoalStateError -> dispatch(Msg.GoalStateError(action.text))
                Action.GoalStateLoading -> dispatch(Msg.GoalStateLoading)
                is Action.GoalStateResult -> dispatch(Msg.GoalStateResult(
                    goal = action.goal,
                    active = action.active
                ))
                Action.LogOut -> publish(Label.ClickBack)
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.SaveData -> {
                    scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        wrapperStoreException({
                            val state = state()
                            updateGoalUseCase(
                               GoalEntity(
                                   active = state.active,
                                   goalType = state.goal
                               )
                            )
                            publish(Label.ClickBack)}
                        ){
                            dispatch(Msg.GoalStateError(it))
                        }
                    }
                }

                is Intent.ChangeActive -> dispatch(Msg.ChangeActive(intent.active))
                is Intent.ChangeGoal -> dispatch(Msg.ChangeGoal(intent.goal))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeActive -> copy(active = message.active)
            is Msg.ChangeGoal -> copy(goal = message.goal)
            is Msg.GoalStateError -> copy(goalState = State.GoalState.Error(message.text))
            Msg.GoalStateLoading -> copy(goalState = State.GoalState.Loading)
            is Msg.GoalStateResult -> copy(goalState = State.GoalState.Result)
        }
    }
}
