package ru.topbun.cherry_tip.presentation.screens.auth.childs.login

import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.LoginEntity

interface LoginComponent {

    val state: StateFlow<LoginStore.State>

    fun clickBack()
    fun onLogin(login: LoginEntity)
    fun clickSignUp()
    fun changeEmail(email: String)
    fun changePassword(password: String)
    fun changeVisiblePassword(value: Boolean)
    fun changeValidPassword()

}