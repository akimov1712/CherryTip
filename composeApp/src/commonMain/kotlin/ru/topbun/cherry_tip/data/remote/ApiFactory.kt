package ru.topbun.cherry_tip.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

interface ApiFactory {
   val client: HttpClient
   fun HttpRequestBuilder.apiUrl(path: String)
}