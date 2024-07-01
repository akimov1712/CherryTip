package ru.topbun.cherry_tip.presentation.screens.auth.login

import ru.topbun.cherry_tip.domain.entity.LoginEntity

interface LoginComponent {

    fun onClickBack()
    fun onClickLogin(login: LoginEntity)
    fun onClickSignUp()

    fun onClickApple()
    fun onClickGoogle()
    fun onClickFacebook()

    fun onChangeEmail(email: String)
    fun onChangePassword(password: String)

}