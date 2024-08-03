package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.useCases.challenge.GetChallengeUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.AddDrinkGlassUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.GetCountGlassUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.State
import ru.topbun.cherry_tip.utills.Const
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException
import kotlin.math.ceil

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object AddDrinkGlass: Intent
        data object OpenChallengeScreen: Intent
        data class OpenChallengeDetailScreen(val id: Int): Intent
    }

    data class State(
        val glassStateStatus: GlassStateStatus,
        val challengeStateStatus: ChallengeStateStatus
    ){
        data class ChallengeState(
            val challengeStatus: List<ChallengeEntity>
        )
        sealed interface ChallengeStateStatus{
            data object Initial: ChallengeStateStatus
            data object Loading: ChallengeStateStatus
            data class Error(val text: String): ChallengeStateStatus
            data class Result(val result: ChallengeState): ChallengeStateStatus
        }

        data class GlassState(
            val consumption: List<Glass> = emptyList(),
            val mlConsumed: Float = 0f,
            val mlTotal: Float = 0f,
            val firstPageIndex: Int = 0,
            val countPages: Int = 0,
        )
        sealed interface GlassStateStatus{
            data object Initial: GlassStateStatus
            data object Loading: GlassStateStatus
            data object Error: GlassStateStatus
            data class Result(val result: GlassState): GlassStateStatus
        }
    }

    sealed interface Label{
        data object OpenChallengeScreen: Label
        data class OpenChallengeDetailScreen(val id: Int): Label
        data object OpenAuthScreen: Label
    }

}

class HomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getChallengeUseCase: GetChallengeUseCase,
    private val addDrinkGlassUseCase: AddDrinkGlassUseCase,
    private val getCountGlassUseCase: GetCountGlassUseCase
) {

    fun create(): HomeStore =
        object : HomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(
                glassStateStatus = State.GlassStateStatus.Initial,
                challengeStateStatus = State.ChallengeStateStatus.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private sealed interface Action {
        data object GlassLoadingStatus: Action
        data object GlassErrorStatus: Action
        data class GlassResultStatus(val state: State.GlassState): Action

        data object ChallengeLoadingStatus: Action
        data class ChallengeErrorStatus(val text: String): Action
        data class ChallengeResultStatus(val state: State.ChallengeState): Action

        data object OpenAuthScreen: Action

    }

    private sealed interface Msg {
        data object GlassLoadingStatus: Msg
        data object GlassErrorStatus: Msg
        data class GlassResultStatus(val state: State.GlassState): Msg

        data object ChallengeLoadingStatus: Msg
        data class ChallengeErrorStatus(val text: String): Msg
        data class ChallengeResultStatus(val state: State.ChallengeState): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            val handlerTokenException = handlerTokenException { dispatch(Action.OpenAuthScreen) }

            scope.launch(handlerTokenException) { sendChallenge() }
            scope.launch(handlerTokenException) { sendGlass() }
        }

        private suspend fun sendChallenge() {
            wrapperStoreException({
                dispatch(Action.ChallengeLoadingStatus)
                val result = getChallengeUseCase(ChallengeStatus.All)
                dispatch(Action.ChallengeResultStatus(State.ChallengeState(result)))
            }){
                dispatch(Action.ChallengeErrorStatus(it))
            }
        }

        private suspend fun sendGlass() {
            wrapperStoreException({
                dispatch(Action.GlassLoadingStatus)
                getCountGlassUseCase().collect {
                    val glassState = State.GlassState(
                        consumption = it.toConsumption(),
                        mlConsumed = it.countDrinkGlass * Const.ML_GLASS / Const.ML_TO_LITER.toFloat(),
                        mlTotal = it.countNeededGlass * Const.ML_GLASS / Const.ML_TO_LITER.toFloat(),
                        firstPageIndex = it.countDrinkGlass / COUNT_GLASS_PAGE,
                        countPages = ceil(it.countNeededGlass.toFloat() / COUNT_GLASS_PAGE).toInt()
                    )
                    dispatch(Action.GlassResultStatus(glassState))
                }
            }){
                dispatch(Action.GlassErrorStatus)
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                Action.GlassErrorStatus -> dispatch(Msg.GlassErrorStatus)
                Action.GlassLoadingStatus -> dispatch(Msg.GlassLoadingStatus)
                is Action.GlassResultStatus -> dispatch(Msg.GlassResultStatus(action.state))
                is Action.ChallengeErrorStatus -> dispatch(Msg.ChallengeErrorStatus(action.text))
                Action.ChallengeLoadingStatus -> dispatch(Msg.ChallengeLoadingStatus)
                is Action.ChallengeResultStatus -> dispatch(Msg.ChallengeResultStatus(action.state))
                Action.OpenAuthScreen -> publish(Label.OpenAuthScreen)
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.AddDrinkGlass -> {
                    scope.launch {
                        addDrinkGlassUseCase()
                    }
                }
                Intent.OpenChallengeScreen -> publish(Label.OpenChallengeScreen)
                is Intent.OpenChallengeDetailScreen -> publish(Label.OpenChallengeDetailScreen(intent.id))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            Msg.GlassErrorStatus -> copy(glassStateStatus = State.GlassStateStatus.Error)
            Msg.GlassLoadingStatus -> copy(glassStateStatus = State.GlassStateStatus.Loading)
            is Msg.GlassResultStatus -> copy(glassStateStatus = State.GlassStateStatus.Result(message.state))
            is Msg.ChallengeErrorStatus -> copy(challengeStateStatus = State.ChallengeStateStatus.Error(message.text))
            Msg.ChallengeLoadingStatus -> copy(challengeStateStatus = State.ChallengeStateStatus.Loading)
            is Msg.ChallengeResultStatus -> copy(challengeStateStatus = State.ChallengeStateStatus.Result(message.state))
        }
    }
}
