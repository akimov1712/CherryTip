package ru.topbun.cherry_tip.utills

import io.ktor.client.call.DoubleReceiveException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.TimeoutCancellationException

fun HttpResponse.exceptionWrapper(): HttpResponse {
    when(this.status.value){
        in(400..499) -> throw ClientException(this.status.description)
        in(500..599) -> throw ServerException(this.status.description)
    }
    return try {
        this
    } catch (e: TimeoutCancellationException){
        throw RequestTimeoutException()
    } catch (e: HttpRequestTimeoutException){
        throw RequestTimeoutException()
    } catch (e: NoTransformationFoundException){
        throw ParseBackendResponseException()
    } catch (e: DoubleReceiveException){
        throw ParseBackendResponseException()
    }
}