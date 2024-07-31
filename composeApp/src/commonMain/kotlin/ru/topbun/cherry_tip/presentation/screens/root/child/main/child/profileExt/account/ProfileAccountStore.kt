package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account.ProfileAccountStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account.ProfileAccountStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account.ProfileAccountStore.State

internal interface ProfileAccountStore : Store<Intent, State, Label> {

    sealed interface Intent {
    }

    data class State(
        val userId: String
    )

    sealed interface Label {
    }
}

internal class ProfileAccountStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ProfileAccountStore =
        object : ProfileAccountStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProfileAccountStore",
            initialState = State(""),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }


    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            else -> this
        }
    }
}
