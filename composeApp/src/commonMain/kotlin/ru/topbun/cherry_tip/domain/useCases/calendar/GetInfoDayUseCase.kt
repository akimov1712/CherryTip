package ru.topbun.cherry_tip.domain.useCases.calendar

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.domain.repository.CalendarRepository

class GetInfoDayUseCase(
    private val repository: CalendarRepository
) {

    suspend operator fun invoke(id: Int) = repository.getInfoDay(id)
    suspend operator fun invoke(date: GMTDate) = repository.getInfoDay(date)

}