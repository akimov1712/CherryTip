package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.LoginEntity

interface LoginComponent {

    val state: StateFlow<LoginStore.State>

    fun onClickBack()
    fun onClickLogin(login: LoginEntity)
    fun onClickSignUp()

    fun onClickApple()
    fun onClickGoogle()
    fun onClickFacebook()

    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)

}