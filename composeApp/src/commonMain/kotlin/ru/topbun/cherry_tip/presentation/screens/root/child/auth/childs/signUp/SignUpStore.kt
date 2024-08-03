package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.auth.SignUpEntity
import ru.topbun.cherry_tip.domain.useCases.auth.SignUpUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpStore.State
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface SignUpStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data object OnSignUp : Intent
        data object ClickLogin : Intent
        data class ChangeUsername(val username: String) : Intent
        data class ChangeUsernameError(val value: Boolean) : Intent
        data class ChangeEmail(val email: String) : Intent
        data class ChangeEmailError(val value: Boolean) : Intent
        data class ChangePassword(val password: String) : Intent
        data class ChangePasswordError(val value: Boolean) : Intent
        data class ChangeConfirmPassword(val confirmPassword: String) : Intent
        data class ChangeConfirmPasswordError(val value: Boolean) : Intent
        data class ChangeVisiblePassword(val value: Boolean) : Intent
    }

    data class State(
        val username: String,
        val usernameIsError: Boolean,
        val email: String,
        val emailIsError: Boolean,
        val password: String,
        val passwordIsError: Boolean,
        val confirmPassword: String,
        val confirmPasswordIsError: Boolean,
        val isVisiblePassword: Boolean,
        val isValidPassword: Boolean,
        val signUpState: SignUpState,
    ) {
        sealed interface SignUpState {

            data object Initial : SignUpState
            data object Loading : SignUpState
            data class Error(val errorText: String) : SignUpState

        }
    }

    sealed interface Label {
        data object OnSignUp : Label
        data object ClickBack : Label
        data object ClickLogin : Label
    }

}

class SignUpStoreFactory(
    private val storeFactory: StoreFactory,
    private val signUpUseCase: SignUpUseCase,
) {

    val store: Store<Intent, State, Label> = storeFactory.create(
        name = "SignUpStore",
        initialState = State(
            username = "",
            usernameIsError = false,
            email = "",
            emailIsError = false,
            password = "",
            passwordIsError = false,
            confirmPassword = "",
            confirmPasswordIsError = false,
            isVisiblePassword = false,
            isValidPassword = false,
            signUpState = State.SignUpState.Initial
        ),
        bootstrapper = null,
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    )

    private sealed interface Action

    private sealed interface Msg {
        data object SignUpLoading : Msg
        data class SignUpError(val messageError: String) : Msg
        data class ChangeUsername(val username: String) : Msg
        data class ChangeEmail(val email: String) : Msg
        data class ChangePassword(val password: String) : Msg
        data class ChangeConfirmPassword(val confirmPassword: String) : Msg
        data class ChangeVisiblePassword(val value: Boolean) : Msg
        data class ChangeUsernameError(val value: Boolean) : Msg
        data class ChangeEmailError(val value: Boolean) : Msg
        data class ChangePasswordError(val value: Boolean) : Msg
        data class ChangeConfirmPasswordError(val value: Boolean) : Msg
        data object CleanState : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            val state = state()
            when (intent) {
                is Intent.ChangeUsername -> dispatch(Msg.ChangeUsername(intent.username))
                is Intent.ChangeEmail -> dispatch(Msg.ChangeEmail(intent.email))
                is Intent.ChangePassword -> dispatch(Msg.ChangePassword(intent.password))
                is Intent.ChangeConfirmPassword -> dispatch(Msg.ChangeConfirmPassword(intent.confirmPassword))
                is Intent.ChangeVisiblePassword -> dispatch(Msg.ChangeVisiblePassword(intent.value))
                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.ClickLogin -> publish(Label.ClickLogin)
                is Intent.OnSignUp -> {
                    scope.launch {
                        wrapperStoreException({
                            dispatch(Msg.SignUpLoading)
                            sendSingUp(state)
                            publish(Label.OnSignUp)
                            dispatch(Msg.CleanState)
                        }){
                            dispatch(Msg.SignUpError(it))
                        }
                    }
                }

                is Intent.ChangeConfirmPasswordError -> dispatch(Msg.ChangeConfirmPasswordError(intent.value))
                is Intent.ChangeEmailError -> dispatch(Msg.ChangeEmailError(intent.value))
                is Intent.ChangePasswordError -> dispatch(Msg.ChangePasswordError(intent.value))
                is Intent.ChangeUsernameError -> dispatch(Msg.ChangeUsernameError(intent.value))
            }
        }

        private suspend fun sendSingUp(state: State){
            val signUp = SignUpEntity(
                username = state.username,
                email = state.email,
                password = state.password
            )
            signUpUseCase(signUp)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeConfirmPassword -> {
                copy(confirmPassword = msg.confirmPassword)
            }

            is Msg.ChangeEmail -> {
                copy(email = msg.email)
            }

            is Msg.ChangePassword -> {
                copy(password = msg.password)
            }

            is Msg.ChangeUsername -> {
                copy(username = msg.username)
            }

            is Msg.ChangeVisiblePassword -> {
                copy(isVisiblePassword = msg.value)
            }

            is Msg.SignUpError -> {
                copy(signUpState = State.SignUpState.Error(msg.messageError))
            }

            Msg.SignUpLoading -> {
                copy(signUpState = State.SignUpState.Loading)
            }

            is Msg.ChangeConfirmPasswordError -> copy(confirmPasswordIsError = msg.value)
            is Msg.ChangeEmailError -> copy(emailIsError = msg.value)
            is Msg.ChangePasswordError -> copy(passwordIsError = msg.value)
            is Msg.ChangeUsernameError -> copy(usernameIsError = msg.value)
            Msg.CleanState -> State(
                username = "",
                usernameIsError = false,
                email = "",
                emailIsError = false,
                password = "",
                passwordIsError = false,
                confirmPassword = "",
                confirmPasswordIsError = false,
                isVisiblePassword = false,
                isValidPassword = false,
                signUpState = State.SignUpState.Initial
            )
        }
    }

}