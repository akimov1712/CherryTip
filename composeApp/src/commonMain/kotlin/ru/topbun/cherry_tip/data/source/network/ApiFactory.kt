package ru.topbun.cherry_tip.data.source.network

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class ApiFactory {

    private companion object{
         const val BASE_URL = "https://cherrytip.ru/"
    }

    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            contentType(ContentType.Application.Json.withParameter("charset", "utf-8"))
            url(BASE_URL)
        }
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Napier.v("HTTP Client", null, message)
                }
            }
            level = LogLevel.ALL
        }
    }.also { Napier.base(DebugAntilog()) }

}

fun HttpRequestBuilder.token(token: String) = header("Authorization", "Bearer $token")