package ru.topbun.cherry_tip.utills

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
open class AppException: RuntimeException()

class ParseBackendResponseException : AppException()
class RequestTimeoutException(val errorText: String = "Timed out") : AppException()
class ClientException(val errorText: String) : AppException()
class ServerException(val errorText: String) : AppException()
class ConnectException(val errorText: String = "A Failed to connect to the server, check your internet connection"): AppException()

class FailedExtractTokenException : AppException()
class AccountInfoNotCompleteException : AppException()


fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

suspend fun wrapperStoreException(tryBlock: suspend () -> Unit, onFinally: () -> Unit = {}, onError: (String) -> Unit){
    try {
        tryBlock()
    } catch (e: AccountInfoNotCompleteException) {
        Napier.e{ e.toString() }
        onError("Account info not complete")
    } catch (e: ClientException) {
        Napier.e{ e.toString() }
        onError(e.errorText)
    } catch (e: ServerException){
        Napier.e{ e.toString() }
        onError(e.errorText)
    } catch (e: RequestTimeoutException) {
        Napier.e{ e.toString() }
        onError(e.errorText)
    } catch (e: ConnectException){
        Napier.e{ e.toString() }
        onError(e.errorText)
    } catch (e: ParseBackendResponseException) {
        Napier.e{ e.toString() }
        onError("Error while receiving data from the server")
    }  finally {
        onFinally()
    }
}

