package ru.topbun.cherry_tip.data.repository

import io.ktor.client.call.body
import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.data.mapper.toEntity
import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import ru.topbun.cherry_tip.data.source.local.getToken
import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarRecipeByTypeDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.SetRecipeToDayResponse
import ru.topbun.cherry_tip.data.source.network.service.CalendarApi
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarRecipeByTypeEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.repository.CalendarRepository
import ru.topbun.cherry_tip.utills.codeResultWrapper
import ru.topbun.cherry_tip.utills.exceptionWrapper
import ru.topbun.cherry_tip.utills.toIso8601

class CalendarRepositoryImpl(
    private val api: CalendarApi,
    private val settings: Settings
): CalendarRepository {

    override suspend fun setRecipeToDay(
        date: GMTDate,
        category: CalendarType,
        recipes: List<Int>
    ): CalendarRecipeByTypeEntity = exceptionWrapper{
        api.setRecipeToDay(
            token = settings.getToken(),
            date = date.toIso8601(),
            category = category,
            recipes = recipes
        ).codeResultWrapper().body<SetRecipeToDayResponse>().toEntity()
    }

    override suspend fun getInfoDay(date: GMTDate): CalendarEntity = exceptionWrapper{
        api.getInfoDay(
            token = settings.getToken(),
            date = date.toIso8601()
        ).codeResultWrapper().body<CalendarDto>().toEntity()
    }

    override suspend fun getInfoDay(id: Int): CalendarEntity = exceptionWrapper{
        api.getInfoDay(
            token = settings.getToken(),
            id = id
        ).codeResultWrapper().body<CalendarDto>().toEntity()
    }
}