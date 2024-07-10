package ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderStore.Intent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderStore.Label
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderStore.State

interface ReminderStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class SetIndexSelected(val index: Int): Intent
        data object FinishedStarted: Intent
    }

    data class State(
        val screens: List<ReminderScreens>,
        val indexSelected: Int
    )

    sealed interface Label {
        data object FinishedStarted: Label
    }
}

class ReminderStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): ReminderStore =
        object : ReminderStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ReminderStore",
            initialState = State(
                screens = listOf(ReminderScreens.Reminder1, ReminderScreens.Reminder2, ReminderScreens.Reminder3),
                indexSelected = 0
            ),
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg{
        data class SetIndexSelected(val index: Int): Msg
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.SetIndexSelected -> dispatch(Msg.SetIndexSelected(intent.index))
                is Intent.FinishedStarted -> publish(Label.FinishedStarted)
            }
        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.SetIndexSelected -> copy(indexSelected = message.index)
        }
    }
}
