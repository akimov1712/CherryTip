package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.mvikotlin.core.store.Store
import ru.topbun.cherry_tip.presentation.screens.auth.login.LoginStore.*

interface LoginStore: Store<Intent, State, Label> {

    sealed interface Intent{

        data object ClickBack: Intent
        data object ClickLogin: Intent
        data object ClickSignUp: Intent
        data object ClickApple: Intent
        data object ClickGoogle: Intent
        data object ClickFacebook: Intent
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