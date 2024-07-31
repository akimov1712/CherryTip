package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile.ProfileStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile.ProfileStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile.ProfileStore.State

interface ProfileStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickAccount: Intent
        data object ClickProfile: Intent
        data object ClickGoals: Intent
        data object ClickUnits: Intent
    }

    data class State(
        val items: List<Profile>
    )

    sealed interface Label {
        data object ClickAccount: Label
        data object ClickProfile: Label
        data object ClickGoals: Label
        data object ClickUnits: Label
    }
}

class ProfileStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ProfileStore =
        object : ProfileStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = State(
                items = Profile.entries
            ),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    private sealed interface Msg {
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Msg, Label>() {

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            else -> this
        }
    }
}
