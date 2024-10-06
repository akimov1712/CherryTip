package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.useCases.calendar.GetInfoDayUseCase
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStore.State
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore
import ru.topbun.cherry_tip.utills.getPeriodDate
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.now
import ru.topbun.cherry_tip.utills.toGMTDate
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface CalendarStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeDay(val day: LocalDate): Intent
        data class ClickAppendMeal(val date: LocalDate, val type: CalendarType): Intent
        data class ClickOpenDetailIngest(val date: LocalDate, val type: CalendarType): Intent
        data object LoadCalendar: Intent
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
        data class ClickAppendMeal(val date: LocalDate, val type: CalendarType): Label
        data class ClickOpenDetailIngest(val date: LocalDate, val type: CalendarType): Label
        data object OpenAuthScreen: Label
    }
}

class CalendarStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val getInfoDayUseCase: GetInfoDayUseCase
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
        data object OpenAuthScreen: Action

        data class SetListDays(val list: List<LocalDate>): Action

        data class CalendarError(val msg: String): Action
        data object CalendarLoading: Action
        data class CalendarResult(val calendar: CalendarEntity): Action
    }

    private sealed interface Msg {
        data class SetListDays(val list: List<LocalDate>): Msg
        data class ChangeDay(val day: LocalDate): Msg

        data class CalendarError(val msg: String): Msg
        data object CalendarLoading: Msg
        data class CalendarResult(val calendar: CalendarEntity): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {

        var job: Job? = null

        override fun invoke() {
            job?.cancel()
            job = scope.launch(handlerTokenException { dispatch(Action.OpenAuthScreen)} ) {
                wrapperStoreException({
                    dispatch(Action.CalendarLoading)
                    val info = getAccountInfoUseCase()
                    val datePeriod = getPeriodDate(info.createdAt)
                    val calendar = getInfoDayUseCase(datePeriod.last().toGMTDate())
                    dispatch(Action.SetListDays(datePeriod))
                    dispatch(Action.CalendarResult(calendar))
                }){
                   dispatch(Action.CalendarError(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private var jobCalendar: Job? = null
        private var jobDay: Job? = null

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.CalendarError -> dispatch(Msg.CalendarError(action.msg))
                Action.CalendarLoading -> dispatch(Msg.CalendarLoading)
                is Action.CalendarResult -> dispatch(Msg.CalendarResult(action.calendar))
                Action.OpenAuthScreen -> publish(Label.OpenAuthScreen)
                is Action.SetListDays -> dispatch(Msg.SetListDays(action.list))
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            val state = state()
            when(intent){
                Intent.LoadCalendar -> {
                    jobCalendar?.cancel()
                    jobCalendar = scope.launch(handlerTokenException { publish(Label.OpenAuthScreen)} ) {
                        wrapperStoreException({
                            dispatch(Msg.CalendarLoading)
                            val info = getAccountInfoUseCase()
                            val datePeriod = getPeriodDate(info.createdAt)
                            val calendar = getInfoDayUseCase(state.selectedDay.toGMTDate())
                            dispatch(Msg.SetListDays(datePeriod))
                            dispatch(Msg.CalendarResult(calendar))
                        }){
                            dispatch(Msg.CalendarError(it))
                        }
                    }
                }
                is Intent.ChangeDay -> {
                    jobDay?.cancel()
                    jobDay = scope.launch(handlerTokenException { publish(Label.OpenAuthScreen)} ) {
                        wrapperStoreException({
                            dispatch(Msg.CalendarLoading)
                            dispatch(Msg.ChangeDay(intent.day))
                            val calendar = getInfoDayUseCase(intent.day.toGMTDate())
                            dispatch(Msg.CalendarResult(calendar))
                        }){
                            dispatch(Msg.CalendarError(it))
                        }
                    }
                }
                is Intent.ClickAppendMeal -> publish(Label.ClickAppendMeal(intent.date, intent.type))
                is Intent.ClickOpenDetailIngest -> publish(Label.ClickOpenDetailIngest(intent.date, intent.type))
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.CalendarError -> copy(calendarState = State.CalendarState.Error(message.msg))
            Msg.CalendarLoading -> copy(calendarState = State.CalendarState.Loading)
            is Msg.CalendarResult -> copy(calendarState = State.CalendarState.Result, calendar = message.calendar)
            is Msg.ChangeDay -> copy(selectedDay = message.day)
            is Msg.SetListDays -> copy(listDays = message.list)
        }
    }
}

