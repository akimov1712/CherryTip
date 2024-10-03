package ru.topbun.cherry_tip.domain.useCases.calendar

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.repository.CalendarRepository

class SetRecipeToDayUseCase(
    private val repository: CalendarRepository
) {

    suspend operator fun invoke(date: GMTDate, category: CalendarType, recipes: List<Int>) = repository.setRecipeToDay(date, category,recipes)

}