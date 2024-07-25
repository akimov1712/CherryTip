package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.State

interface ChallengeDetailStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack: Intent
    }

    data class State(val todo: Unit = Unit)

    sealed interface Label {
        data object ClickBack: Label
    }
}

class ChallengeDetailStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ChallengeDetailStore =
        object : ChallengeDetailStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ChallengeDetailStore",
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
                Intent.ClickBack -> publish(Label.ClickBack)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            else -> {copy()}
        }
    }
}
