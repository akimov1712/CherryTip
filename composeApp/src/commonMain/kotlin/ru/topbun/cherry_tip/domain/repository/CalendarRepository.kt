package ru.topbun.cherry_tip.domain.repository

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.data.source.network.dto.calendar.MealDto
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.entity.calendar.MealEntity

interface CalendarRepository {

    suspend fun setRecipeToDay(date: GMTDate, category: CalendarType, recipes: List<Int>): MealEntity
    suspend fun getInfoDay(date: GMTDate): CalendarEntity
    suspend fun getInfoDay(id: Int): CalendarEntity

}