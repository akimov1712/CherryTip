package ru.topbun.cherry_tip.presentation.screens.auth

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpComponent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyComponent

interface AuthComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{

        data class Login(val component: LoginComponent): Child
        data class SignUp(val component: SignUpComponent): Child
        data class Survey(val component: SurveyComponent): Child

    }

}