package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.LoginEntity

interface LoginComponent {

    val state: StateFlow<LoginStore.State>

    fun clickBack()
    fun clickLogin(login: LoginEntity)
    fun clickSignUp()
    fun clickApple()
    fun clickGoogle()
    fun clickFacebook()
    fun changeEmail(email: String)
    fun changePassword(password: String)

}