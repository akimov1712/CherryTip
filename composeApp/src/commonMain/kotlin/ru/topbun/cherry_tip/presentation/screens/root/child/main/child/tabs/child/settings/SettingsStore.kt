package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsStore.State

interface SettingsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickAccount: Intent
        data object ClickProfile: Intent
        data object ClickGoals: Intent
        data object ClickUnits: Intent
    }

    data class State(
        val items: List<Settings>
    )

    sealed interface Label {
        data object ClickAccount: Label
        data object ClickProfile: Label
        data object ClickGoals: Label
        data object ClickUnits: Label
    }
}

class SettingsStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): SettingsStore =
        object : SettingsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = State(
                items = Settings.entries
            ),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    private sealed interface Msg {
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Nothing, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.ClickAccount -> publish(Label.ClickAccount)
                Intent.ClickGoals -> publish(Label.ClickGoals)
                Intent.ClickProfile -> publish(Label.ClickProfile)
                Intent.ClickUnits -> publish(Label.ClickUnits)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            else -> this
        }
    }
}
