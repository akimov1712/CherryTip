package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.utills.componentScope

class CalendarComponentImpl(
    componentContext: ComponentContext,
    onClickAppendMeal: (CalendarType) -> Unit,
    onOpenAuth: () -> Unit,
    onClickBack: () -> Unit,
    private val storeFactory: CalendarStoreFactory
): CalendarComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    is CalendarStore.Label.ClickAppendMeal -> onClickAppendMeal(it.type)
                    CalendarStore.Label.OpenAuthScreen -> onOpenAuth()
                    CalendarStore.Label.ClickBack -> onClickBack()
                }
            }
        }
    }
    override fun clickBack() = store.accept(CalendarStore.Intent.ClickBack)
    override fun loadCalendar() = store.accept(CalendarStore.Intent.LoadCalendar)
    override fun changeDay(day: LocalDate) = store.accept(CalendarStore.Intent.ChangeDay(day))
    override fun openAppendMeal(type: CalendarType) = store.accept(CalendarStore.Intent.ClickAppendMeal(type))
}