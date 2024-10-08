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
        e.printStackTrace()
        throw e
    } catch (e: ServerException){
        e.printStackTrace()
        throw e
    } catch (e: TimeoutCancellationException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: ConnectTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: HttpRequestTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: NoTransformationFoundException){
        e.printStackTrace()
        throw ParseBackendResponseException()
    } catch (e: DoubleReceiveException){
        e.printStackTrace()
        throw ParseBackendResponseException()
    } catch (e: FailedExtractTokenException){
        e.printStackTrace()
        throw FailedExtractTokenException()
    } catch (e: SocketTimeoutException){
        e.printStackTrace()
        throw RequestTimeoutException()
    } catch (e: IllegalStateException){
        e.printStackTrace()
        throw ConnectException("Error sending data")
    } catch (e: Exception){
        e.printStackTrace()
        throw ConnectException()
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