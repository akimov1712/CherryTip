package ru.topbun.cherry_tip.domain.repository

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

interface CalendarRepository {

    suspend fun setRecipeToDay(date: GMTDate, category: CalendarType, recipes: List<Int>): CalendarEntity
    suspend fun getInfoDay(date: GMTDate): CalendarEntity
    suspend fun getInfoDay(id: Int): CalendarEntity

}