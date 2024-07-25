package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.State

interface ChallengeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnClick: Intent
    }

    data class State(val todo: Unit = Unit)

    sealed interface Label {
        data object OnClick: Label
    }
}

class ChallengeStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ChallengeStore =
        object : ChallengeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ChallengeStore",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.OnClick -> publish(Label.OnClick)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            else -> { copy() }
        }
    }
}
