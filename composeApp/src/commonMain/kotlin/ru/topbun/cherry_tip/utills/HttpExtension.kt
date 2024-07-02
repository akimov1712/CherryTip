package ru.topbun.cherry_tip.utills

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.TimeoutCancellationException

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
    } catch (e: Exception){
        throw ConnectException()
    }
}

fun HttpResponse.codeResultWrapper() = when(this.status.value){
    in(400..499) -> throw ClientException(this.status.description)
    in(500..599) -> throw ServerException(this.status.description)
    else -> this
}
