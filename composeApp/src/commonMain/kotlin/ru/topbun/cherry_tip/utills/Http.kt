package ru.topbun.cherry_tip.utills

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.TimeoutCancellationException
import ru.topbun.cherry_tip.data.source.network.dto.ErrorDto

suspend fun <T>exceptionWrapper(block: suspend () -> T): T {
    return try {
        block()
    } catch (e: TimeoutCancellationException){
        throw RequestTimeoutException()
    } catch (e: ConnectTimeoutException){
        throw RequestTimeoutException()
    } catch (e: HttpRequestTimeoutException){
        throw RequestTimeoutException()
    } catch (e: NoTransformationFoundException){
        throw ParseBackendResponseException()
    } catch (e: DoubleReceiveException){
        throw ParseBackendResponseException()
    } catch (e: SocketTimeoutException){
        throw RequestTimeoutException()
    }
}

suspend fun HttpResponse.codeResultWrapper() = when(this.status.value){
    in (400..499) -> throw ClientException( createErrorMessage(this) )
    in (500..599) -> throw ServerException( createErrorMessage(this) )
    else -> this
}

private suspend fun createErrorMessage(httpResponse: HttpResponse)
    = httpResponse.body<ErrorDto>().message.first()