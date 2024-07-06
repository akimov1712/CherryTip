package ru.topbun.cherry_tip.presentation.screens.auth.childs.login

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.auth.LoginEntity
import ru.topbun.cherry_tip.domain.useCases.auth.LoginUseCase
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStore.Intent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStore.Label
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStore.State
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpStore
import ru.topbun.cherry_tip.utills.ClientException
import ru.topbun.cherry_tip.utills.ConnectException
import ru.topbun.cherry_tip.utills.ParseBackendResponseException
import ru.topbun.cherry_tip.utills.RequestTimeoutException
import ru.topbun.cherry_tip.utills.ServerException

interface LoginStore: Store<Intent, State, Label> {

    sealed interface Intent{

        data object ClickBack: Intent
        data object OnLogin: Intent
        data object ClickSignUp: Intent
        data class ChangeEmail(val email: String): Intent
        data class ChangePassword(val password: String): Intent
        data class ChangeVisiblePassword(val value: Boolean): Intent
    }

    data class State(
        val email: String,
        val password: String,
        val isVisiblePassword: Boolean,
        val isValidPassword: Boolean,
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
        data object OnLogin: Label
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
            isVisiblePassword = false,
            isValidPassword = false,
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
        data object OnLogin: Msg
        data class ChangeEmail(val email: String): Msg
        data class ChangePassword(val password: String): Msg
        data class ChangeVisiblePassword(val value: Boolean): Msg
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>(){

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            val state = state()
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
                    scope.launch {
                        dispatch(Msg.LoginLoading)
                        try {
                            sendLogin(state)
                            publish(Label.OnLogin)
                            dispatch(Msg.OnLogin)
                        } catch (e: ParseBackendResponseException) {
                            dispatch(Msg.LoginError("Error while receiving data from the server"))
                        } catch (e: RequestTimeoutException) {
                            dispatch(Msg.LoginError("Timed out"))
                        } catch (e: ClientException) {
                            dispatch(Msg.LoginError(e.errorText))
                        } catch (e: ServerException) {
                            dispatch(Msg.LoginError(e.errorText))
                        } catch (e: ConnectException) {
                            dispatch(Msg.LoginError("A Failed to connect to the server, check your internet connection"))
                        }
                    }
                }

                is Intent.ChangeVisiblePassword -> {
                    dispatch(Msg.ChangeVisiblePassword(intent.value))
                }
            }
        }

        private suspend fun sendLogin(state: State){
            val login = LoginEntity(
                email = state.email,
                password = state.password
            )
            loginUseCase(login)
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
            is Msg.ChangeVisiblePassword -> {
                copy(isVisiblePassword = msg.value)
            }
        }
    }

}