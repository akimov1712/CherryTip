package ru.topbun.cherry_tip.presentation.screens.auth

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.mvikotlin.core.store.StoreFactory
import org.koin.compose.getKoin
import ru.topbun.cherry_tip.domain.repository.UserRepository
import ru.topbun.cherry_tip.domain.useCases.user.CreateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateUnitsUseCase
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginContent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderScreen
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpContent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyComponent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyScreen
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyStoreFactory
import ru.topbun.cherry_tip.presentation.screens.splash.SplashScreen

@Composable
fun AuthContent(
    component: AuthComponent,
) {
    Children(
        stack = component.stack,
        animation = stackAnimation { child ->
            when(child.instance){
                is AuthComponent.Child.Login -> fade() + scale(frontFactor = 1.10f)
                is AuthComponent.Child.SignUp -> fade() + scale(frontFactor = 1.10f)
                is AuthComponent.Child.Survey -> fade() + scale(frontFactor = 1.10f)
                is AuthComponent.Child.Splash -> null
                is AuthComponent.Child.Reminder -> fade() + scale(frontFactor = 1.10f)
            }
        }
    ){
        when(val instance = it.instance){
            is AuthComponent.Child.Login -> LoginContent(instance.component)
            is AuthComponent.Child.SignUp -> SignUpContent(instance.component)
            is AuthComponent.Child.Survey -> SurveyScreen(instance.component)
            is AuthComponent.Child.Splash -> SplashScreen(instance.component)
            is AuthComponent.Child.Reminder -> ReminderScreen(instance.component)
        }
    }
}