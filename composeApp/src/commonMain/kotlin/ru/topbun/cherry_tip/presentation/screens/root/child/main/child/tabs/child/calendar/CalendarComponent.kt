package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

interface CalendarComponent {

    val state: StateFlow<CalendarStore.State>

    fun clickBack()
    fun loadCalendar()
    fun changeDay(day: LocalDate)
    fun openAppendMeal(type: CalendarType)

}