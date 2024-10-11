package ru.topbun.cherry_tip.utills

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.account_not_complete
import cherrytip.composeapp.generated.resources.check_internet_connection
import cherrytip.composeapp.generated.resources.error_response
import cherrytip.composeapp.generated.resources.fat
import cherrytip.composeapp.generated.resources.timed_out
import kotlinx.coroutines.CoroutineExceptionHandler
import org.jetbrains.compose.resources.getString

open class AppException: RuntimeException()

class ParseBackendResponseException(val errorText: String = "") : AppException()
class RequestTimeoutException(val errorText: String = "") : AppException()
class ClientException(val errorText: String = "") : AppException()
class ServerException(val errorText: String = "") : AppException()
class ConnectException(val errorText: String = ""): AppException()

class FailedExtractTokenException : AppException()
class AccountInfoNotCompleteException(val errorText: String = "") : AppException()


fun handlerTokenException(action: () -> Unit) = CoroutineExceptionHandler { _, throwable ->
    if (throwable is FailedExtractTokenException) action()
}

suspend fun wrapperStoreException(tryBlock: suspend () -> Unit, onFinally: () -> Unit = {}, onError: (String) -> Unit){
    try {
        tryBlock()
    } catch (e: AccountInfoNotCompleteException) {
        onError(getString(Res.string.account_not_complete))
    } catch (e: ClientException) {
        onError(e.errorText)
    } catch (e: ServerException){
        onError(e.errorText)
    } catch (e: RequestTimeoutException) {
        onError(getString(Res.string.timed_out))
    } catch (e: ConnectException){
        onError(getString(Res.string.check_internet_connection))
    } catch (e: ParseBackendResponseException) {
        onError(getString(Res.string.error_response))
    }  finally {
        onFinally()
    }
}

