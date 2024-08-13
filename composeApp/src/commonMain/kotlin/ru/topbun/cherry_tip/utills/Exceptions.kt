package ru.topbun.cherry_tip.utills

import kotlinx.coroutines.CoroutineExceptionHandler
open class AppException: RuntimeException()

class ParseBackendResponseException : AppException()
class RequestTimeoutException : AppException()
class ClientException(val errorText: String) : AppException()
class ServerException(val errorText: String) : AppException()
class ConnectException : AppException()

class FailedExtractTokenException : AppException()
class AccountInfoNotCompleteException : AppException()


fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

suspend fun wrapperStoreException(tryBlock: suspend () -> Unit, onFinally: () -> Unit = {}, onError: (String) -> Unit){
    try {
        tryBlock()
    } catch (e: AccountInfoNotCompleteException) {
        onError("Account info not complete")
    } catch (e: RequestTimeoutException) {
        onError("Timed out")
    } catch (e: ParseBackendResponseException) {
        onError("Error while receiving data from the server")
    } catch (e: ClientException) {
        onError(e.errorText)
    } catch (e: ServerException){
        onError(e.errorText)
    } catch (e: ConnectException){
        onError("A Failed to connect to the server, check your internet connection")
    } finally {
        onFinally()
    }
}

