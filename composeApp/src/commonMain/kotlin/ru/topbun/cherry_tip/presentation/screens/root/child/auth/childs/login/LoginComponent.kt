package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login

import kotlinx.coroutines.flow.StateFlow

interface LoginComponent {

    val state: StateFlow<LoginStore.State>

    fun clickBack()
    fun onLogin()
    fun clickSignUp()
    fun changeEmail(email: String)
    fun changePassword(password: String)
    fun changeVisiblePassword(value: Boolean)

}