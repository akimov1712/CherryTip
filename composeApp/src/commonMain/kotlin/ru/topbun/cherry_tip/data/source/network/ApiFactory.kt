package ru.topbun.cherry_tip.data.source.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
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
         const val BASE_URL = "http://10.0.2.2:3000/"
    }

    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        defaultRequest {
            contentType(ContentType.Application.Json.withParameter("charset", "utf-8"))
            url(BASE_URL)
        }
    }

}

fun HttpRequestBuilder.token(token: String) = header("Authorization", "Bearer $token")