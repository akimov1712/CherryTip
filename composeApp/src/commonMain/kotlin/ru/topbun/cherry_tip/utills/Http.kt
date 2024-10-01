package ru.topbun.cherry_tip.utills

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import ru.topbun.cherry_tip.data.source.network.dto.ErrorDto

suspend fun <T>exceptionWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: ClientException){
        println(e.toString())
        throw e
    } catch (e: ServerException){
        println(e.toString())
        throw e
    } catch (e: TimeoutCancellationException){
        println(e.toString())
        throw RequestTimeoutException()
    } catch (e: ConnectTimeoutException){
        println(e.toString())
        throw RequestTimeoutException()
    } catch (e: HttpRequestTimeoutException){
        println(e.toString())
        throw RequestTimeoutException()
    } catch (e: NoTransformationFoundException){
        println(e.toString())
        throw ParseBackendResponseException()
    } catch (e: DoubleReceiveException){
        println(e.toString())
        throw ParseBackendResponseException()
    } catch (e: SocketTimeoutException){
        println(e.toString())
        throw RequestTimeoutException()
    } catch (e: IllegalStateException){
        println(e.toString())
        throw ConnectException("Error sending data")
    }
}

suspend fun HttpResponse.codeResultWrapper() = when(this.status.value) {
    in (400..499) -> throw ClientException(createErrorMessage(this))
    in (500..599) -> throw ServerException(createErrorMessage(this))
    else -> this
}


private suspend fun createErrorMessage(httpResponse: HttpResponse): String {
    val responseBody = httpResponse.bodyAsText()
    val errorDto = Json.decodeFromString<ErrorDto>(responseBody)
     return when {
        errorDto.message is JsonArray && errorDto.message.isNotEmpty() -> {
            errorDto.message[0].jsonPrimitive.content
        }
        else -> {
            errorDto.message.jsonPrimitive.content
        }
    }

}