package ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.auth.SignUpEntity
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponent

interface SignUpComponent {

    val state: StateFlow<SignUpStore.State>

    fun clickBack()
    fun onSignUp(signUp: SignUpEntity)
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

}