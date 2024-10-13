package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp

import kotlinx.coroutines.flow.StateFlow

interface SignUpComponent {

    val state: StateFlow<SignUpStore.State>

    fun clickBack()
    fun onSignUp()
    fun clickLogin()
    fun changeUsername(username: String)
    fun changeUsernameError(value: Boolean)
    fun changeEmail(email: String)
    fun changeEmailError(value: Boolean)
    fun changePassword(password: String)
    fun changePasswordError(value: Boolean)
    fun changeConfirmPassword(confirmPassword: String)
    fun changeConfirmPasswordError(value: Boolean)
    fun changeVisiblePassword(value: Boolean)
    fun changeVisibleConfirmPassword(value: Boolean)

}