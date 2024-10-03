package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.State
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore
import ru.topbun.cherry_tip.utills.now

internal interface CalendarStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeDay(val day: LocalDate): Intent
        data object ClickAppendMeal: Intent
    }

    data class State(
        val listDays: List<LocalDate>,
        val selectedDay: LocalDate,
        val calendar: CalendarEntity?,
        val calendarState: CalendarState
    ){
        sealed interface CalendarState{
            data object Initial: CalendarState
            data object Loading: CalendarState
            data class Error(val msg: String): CalendarState
            data object Result: CalendarState
        }
    }

    sealed interface Label {
        data object ClickAppendMeal: Label
        data object OpenAuthScreen: Label
    }
}

internal class CalendarStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): CalendarStore =
        object : CalendarStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CalendarStore",
            initialState = State(
                listDays = emptyList(),
                selectedDay = LocalDate.now(),
                calendar = null,
                State.CalendarState.Initial
            ),
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

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {

            else -> { TODO() }
        }
    }
}

