package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateUnitsUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface UnitsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object SaveData: Intent
        data object ClickBack: Intent

        data class ChangeWeight(val weight: Int): Intent
        data class ChangeTargetWeight(val targetWeight: Int): Intent
        data class ChangeHeight(val height: Int): Intent
        data class ChangeBloodGlucose(val bloodGlucose: Int): Intent
    }

    data class State(
        val weight: Int,
        val targetWeight: Int,
        val height: Int,
        val bloodGlucose: Int,
        val unitsState: UnitsState
    ){

        sealed interface UnitsState{
            data object Initial: UnitsState
            data object Loading: UnitsState
            data class Error(val text: String): UnitsState
            data object Result: UnitsState
        }
    }

    sealed interface Label {
        data object ClickBack: Label
        data object LogOut: Label
    }
}

class UnitsStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val updateUnitsUseCase: UpdateUnitsUseCase
) {

    fun create(): UnitsStore =
        object : UnitsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "UnitsStore",
            initialState = State(
                weight = 0,
                targetWeight = 0,
                height = 0,
                bloodGlucose = 0,
                unitsState = State.UnitsState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class UnitsStateError(val text: String): Action
        data object UnitsStateLoading: Action
        data class UnitsStateResult(
            val weight: Int,
            val targetWeight: Int,
            val height: Int,
            val bloodGlucose: Int,
        ): Action

        data object LogOut: Action
    }

    private sealed interface Msg {
        data class ChangeWeight(val weight: Int): Msg
        data class ChangeTargetWeight(val targetWeight: Int): Msg
        data class ChangeHeight(val height: Int): Msg
        data class ChangeBloodGlucose(val bloodGlucose: Int): Msg

        data class UnitsStateError(val text: String): Msg
        data object UnitsStateLoading: Msg
        data class UnitsStateResult(
            val weight: Int,
            val targetWeight: Int,
            val height: Int,
            val bloodGlucose: Int,
        ): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.UnitsStateLoading)
                    val accountInfo = getAccountInfoUseCase()
                    dispatch(Action.UnitsStateResult(
                        weight = accountInfo.units?.weight ?: 0,
                        targetWeight = accountInfo.units?.targetWeight ?: 0,
                        height = accountInfo.units?.height ?: 0,
                        bloodGlucose = accountInfo.units?.bloodGlucose ?: 0,
                    ))
                }){
                    dispatch(Action.UnitsStateError(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.UnitsStateError -> dispatch(Msg.UnitsStateError(action.text))
                Action.UnitsStateLoading -> dispatch(Msg.UnitsStateLoading)
                is Action.UnitsStateResult -> dispatch(Msg.UnitsStateResult(
                    weight = action.weight,
                    targetWeight = action.targetWeight,
                    height = action.height,
                    bloodGlucose = action.bloodGlucose
                ))
                Action.LogOut -> publish(Label.ClickBack)
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                is Intent.ChangeBloodGlucose -> dispatch(Msg.ChangeWeight(intent.bloodGlucose))
                is Intent.ChangeHeight -> dispatch(Msg.ChangeWeight(intent.height))
                is Intent.ChangeTargetWeight -> dispatch(Msg.ChangeWeight(intent.targetWeight))
                is Intent.ChangeWeight -> dispatch(Msg.ChangeWeight(intent.weight))


                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.SaveData -> {
                    scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        wrapperStoreException({
                            val state = state()
                            updateUnitsUseCase(
                                UnitsEntity(
                                    weight = state.weight,
                                    height = state.height,
                                    targetWeight = state.targetWeight,
                                    bloodGlucose = state.bloodGlucose
                                )
                            )
                            publish(Label.ClickBack)}
                        ){
                            dispatch(Msg.UnitsStateError(it))
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeBloodGlucose -> copy(bloodGlucose = message.bloodGlucose)
            is Msg.ChangeHeight -> copy(height = message.height)
            is Msg.ChangeTargetWeight -> copy(targetWeight = message.targetWeight)
            is Msg.ChangeWeight -> copy(weight = message.weight)
            is Msg.UnitsStateError -> copy(unitsState = State.UnitsState.Error(message.text))
            Msg.UnitsStateLoading -> copy(unitsState = State.UnitsState.Loading)
            is Msg.UnitsStateResult -> copy(unitsState = State.UnitsState.Result)
        }
    }
}
