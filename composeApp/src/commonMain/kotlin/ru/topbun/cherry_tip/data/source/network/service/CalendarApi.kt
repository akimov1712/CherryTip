package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.token
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

class CalendarApi(
    private val api: ApiFactory
) {

    @Serializable
    private data class SetRecipeToDay(
        @SerialName("date") val date: String,
        @SerialName("category") val category: CalendarType,
        @SerialName("recipes") val recipes: List<Int>
    )

    suspend fun setRecipeToDay(
        token: String,
        date: String,
        category: CalendarType,
        recipes: List<Int>
    ) = api.client.post("/v1/calendar/day/recipes") {
        token(token)
        setBody(SetRecipeToDay(date, category, recipes))
    }

    suspend fun getInfoDay(token: String, date: String) = api.client.get("/v1/calendar/day") {
        token(token)
        parameter(PARAM_KEY_DATE, date)
    }

    suspend fun getInfoDay(token: String, id: Int) = api.client.get("/v1/calendar/day/$id") {
        token(token)
    }

    private companion object {
        private const val PARAM_KEY_DATE = "date"
        private const val PARAM_KEY_CATEGORY = "category"
        private const val PARAM_KEY_RECIPES = "recipes"
    }

}