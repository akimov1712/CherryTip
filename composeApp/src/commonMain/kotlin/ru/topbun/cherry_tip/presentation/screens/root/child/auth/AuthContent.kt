package ru.topbun.cherry_tip.presentation.screens.root.child.auth

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash.SplashScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyScreen

@Composable
fun AuthScreen(
    component: AuthComponent,
) {
    Children(
        stack = component.stack,
        animation = stackAnimation { child ->
            when(child.instance){
                is AuthComponent.Child.Login -> defaultAnimationScreen
                is AuthComponent.Child.SignUp -> defaultAnimationScreen
                is AuthComponent.Child.Survey -> defaultAnimationScreen
                is AuthComponent.Child.Splash -> null
                is AuthComponent.Child.Reminder -> defaultAnimationScreen
            }
        }
    ){
        when(val instance = it.instance){
            is AuthComponent.Child.Login -> LoginScreen(instance.component)
            is AuthComponent.Child.SignUp -> SignUpScreen(instance.component)
            is AuthComponent.Child.Survey -> SurveyScreen(instance.component)
            is AuthComponent.Child.Splash -> SplashScreen(instance.component)
            is AuthComponent.Child.Reminder -> ReminderScreen(instance.component)
        }
    }
}

private val defaultAnimationScreen = fade() + scale(frontFactor = 1.10f)