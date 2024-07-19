package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.useCases.glass.AddDrinkGlassUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.GetCountGlassUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.State
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.Const
import ru.topbun.cherry_tip.utills.RequestTimeoutException
import kotlin.math.ceil

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object AddDrinkGlass: Intent
    }

    data class State(
        val glassStateStatus: GlassStateStatus
    ){
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
            data class Result(val state: GlassState): GlassStateStatus
        }
    }

    sealed interface Label

}

class HomeStoreFactory(
    private val storeFactory: StoreFactory,
    private val addDrinkGlassUseCase: AddDrinkGlassUseCase,
    private val getCountGlassUseCase: GetCountGlassUseCase
) {

    fun create(): HomeStore =
        object : HomeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "HomeStore",
            initialState = State(
                glassStateStatus = State.GlassStateStatus.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private sealed interface Action {
        data object GlassLoadingStatus: Action
        data object GlassErrorStatus: Action
        data class GlassResultStatus(val state: State.GlassState): Action
    }

    private sealed interface Msg {
        data object GlassLoadingStatus: Msg
        data object GlassErrorStatus: Msg
        data class GlassResultStatus(val state: State.GlassState): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                try {
                    dispatch(Action.GlassLoadingStatus)
                    getCountGlassUseCase().collect{
                        val glassState = State.GlassState(
                            consumption = it.toConsumption(),
                            mlConsumed = it.countDrinkGlass * Const.ML_GLASS / Const.ML_TO_LITER.toFloat(),
                            mlTotal = it.countNeededGlass * Const.ML_GLASS / Const.ML_TO_LITER.toFloat(),
                            firstPageIndex = it.countDrinkGlass / COUNT_GLASS_PAGE,
                            countPages = ceil(it.countNeededGlass.toFloat() / COUNT_GLASS_PAGE).toInt()
                        )
                        dispatch(Action.GlassResultStatus(glassState))
                    }
                } catch (e: AccountInfoNotCompleteException){
                    dispatch(Action.GlassErrorStatus)
                } catch (e: RequestTimeoutException){
                    dispatch(Action.GlassErrorStatus)
                }
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
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            Msg.GlassErrorStatus -> copy(glassStateStatus = State.GlassStateStatus.Error)
            Msg.GlassLoadingStatus -> copy(glassStateStatus = State.GlassStateStatus.Loading)
            is Msg.GlassResultStatus -> copy(glassStateStatus = State.GlassStateStatus.Result(message.state))
        }
    }
}
