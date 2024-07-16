package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity
import ru.topbun.cherry_tip.domain.useCases.glass.AddDrinkGlassUseCase
import ru.topbun.cherry_tip.domain.useCases.glass.GetCountGlassUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore.State

interface HomeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object AddDrinkGlass: Intent
    }

    data class State(
        val consumption: List<Glass>,
        val mlConsumed: Float,
        val mlTotal: Float,
        val firstPageIndex: Int,
        val countPages: Int,
    )

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
            initialState = State(emptyList(), 0f, 0f, 0, 0),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private sealed interface Action {
        data class GetCountGlass(
            val consumption: List<Glass>,
            val mlConsumed: Float,
            val mlTotal: Float,
            val firstPageIndex: Int,
            val countPages: Int,
        ): Action
    }

    private sealed interface Msg {
        data class GetCountGlass(
            val consumption: List<Glass>,
            val mlConsumed: Float,
            val mlTotal: Float,
            val firstPageIndex: Int,
            val countPages: Int,
        ): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getCountGlassUseCase().collect{
                    dispatch(
                        Action.GetCountGlass(
                            consumption = it.toConsumption(),
                            mlConsumed = it.countDrinkGlass * 250 / 1000f,
                            mlTotal = it.countNeededGlass * 250 / 1000f,
                            firstPageIndex = it.countDrinkGlass / 5,
                            countPages = it.countNeededGlass / 5 + 1
                        )
                    )
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.GetCountGlass -> dispatch(
                    Msg.GetCountGlass(
                        consumption =  action.consumption,
                        mlConsumed = action.mlConsumed,
                        mlTotal = action.mlTotal,
                        firstPageIndex = action.firstPageIndex,
                        countPages = action.countPages
                    )
                )
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
            is Msg.GetCountGlass -> copy(
                consumption = message.consumption,
                mlConsumed = message.mlConsumed,
                mlTotal = message.mlTotal,
                firstPageIndex = message.firstPageIndex,
                countPages = message.countPages,
            )
        }
    }
}
