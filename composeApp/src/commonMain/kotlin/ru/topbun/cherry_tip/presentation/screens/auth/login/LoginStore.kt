package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.di.useCaseModule
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.useCases.auth.LoginUseCase
import ru.topbun.cherry_tip.presentation.screens.auth.login.LoginStore.*
import ru.topbun.cherry_tip.presentation.screens.auth.login.LoginStoreFactory.ExecutorImpl
import ru.topbun.cherry_tip.utills.ClientException
import ru.topbun.cherry_tip.utills.ParseBackendResponseException
import ru.topbun.cherry_tip.utills.RequestTimeoutException
import ru.topbun.cherry_tip.utills.ServerException

interface LoginStore: Store<Intent, State, Label> {

    sealed interface Intent{

        data object ClickBack: Intent
        data class OnLogin(val login: LoginEntity): Intent
        data object ClickSignUp: Intent
        data class ChangeEmail(val email: String): Intent
        data class ChangePassword(val password: String): Intent

    }

    data class State(
        val email: String,
        val password: String,
        val loginState: LoginState
    ){
        sealed interface LoginState{

            data object Initial: LoginState
            data object Loading: LoginState
            data object Success: LoginState
            data class Error(val errorText: String): LoginState

        }
    }

    sealed interface Label{
        data object ClickBack: Label
        data object ClickSignUp: Label
    }

}

class LoginStoreFactory(
    private val storeFactory: StoreFactory,
    private val loginUseCase: LoginUseCase
){

    val store: Store<Intent, State, Label> = storeFactory.create(
        name = "LoginStore",
        initialState = State(
            email = "",
            password = "",
            loginState = State.LoginState.Initial
        ),
        bootstrapper = null,
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    )

    private sealed interface Action

    private sealed interface Msg{
        data object LoginLoading: Msg
        data class LoginError(val messageError: String): Msg
        data class OnLogin(val login: LoginEntity): Msg
        data class ChangeEmail(val email: String): Msg
        data class ChangePassword(val password: String): Msg
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>(){

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                is Intent.ChangeEmail -> {
                    dispatch(Msg.ChangeEmail(intent.email))
                }
                is Intent.ChangePassword -> {
                    dispatch(Msg.ChangePassword(intent.password))
                }
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
                Intent.ClickSignUp -> {
                    publish(Label.ClickSignUp)
                }
                is Intent.OnLogin -> {
                    dispatch(Msg.LoginLoading)
                    try {
                        scope.launch {
                            loginUseCase(intent.login)
                            dispatch(Msg.OnLogin(intent.login))
                        }
                    } catch(e: ParseBackendResponseException){
                        dispatch(Msg.LoginError("Error while receiving data from the server"))
                    } catch(e: RequestTimeoutException){
                        dispatch(Msg.LoginError("Timed out"))
                    } catch(e: ClientException){
                        dispatch(Msg.LoginError("An error has occurred on your device"))
                    } catch(e: ServerException){
                        dispatch(Msg.LoginError("An error occurred on the server side"))
                    }
                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg>{
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.ChangeEmail -> {
                copy(email = msg.email)
            }
            is Msg.ChangePassword -> {
                copy(password = msg.password)
            }
            Msg.LoginLoading -> {
                copy(loginState = State.LoginState.Loading)
            }
            is Msg.OnLogin -> {
                copy(loginState = State.LoginState.Success)
            }
            is Msg.LoginError -> {
                copy(loginState = State.LoginState.Error(msg.messageError))
            }
        }
    }

}