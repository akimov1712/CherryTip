package ru.topbun.cherry_tip.utills

import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDate.Companion.now(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return Clock.System.now().toLocalDateTime(timeZone).date
}

fun LocalDate.toGMTDate() =
    GMTDate(1,1,1,this.dayOfMonth, Month.from(this.monthNumber), this.year)

