package ru.topbun.cherry_tip.presentation.screens.root.child.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash.SplashComponent

interface AuthComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{

        data class Login(val component: LoginComponent): Child
        data class SignUp(val component: SignUpComponent): Child
        data class Survey(val component: SurveyComponent): Child
        data class Splash(val component: SplashComponent): Child
        data class Reminder(val component: ReminderComponent): Child

    }

}