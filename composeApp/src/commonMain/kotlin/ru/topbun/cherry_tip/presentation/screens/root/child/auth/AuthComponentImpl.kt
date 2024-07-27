package ru.topbun.cherry_tip.presentation.screens.root.child.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash.SplashComponentImpl

class AuthComponentImpl(
    componentContext: ComponentContext,
    private val onAuthFinished: () -> Unit
): AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Splash,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config, componentContext: ComponentContext
    ): AuthComponent.Child{
        return when(config){
            Config.Login -> {
                val onClickBack = { navigation.pop()}
                val onClickSignUp = { navigation.pushToFront(Config.SignUp) }
                val onAccountInfoNotComplete = { navigation.replaceAll(Config.Survey) }
                val onLogin = { navigation.replaceAll(Config.Reminder) }
                val loginComponent: LoginComponentImpl = getKoin().get {
                    parametersOf(componentContext, onClickBack, onClickSignUp, onLogin, onAccountInfoNotComplete)
                }
                AuthComponent.Child.Login(
                    loginComponent
                )
            }

            Config.SignUp -> {
                val onClickBack = { navigation.pop()}
                val onLogin = { navigation.pushToFront(Config.Login) }
                val signUp = { navigation.pushToFront(Config.Survey) }
                val signUpComponent: SignUpComponentImpl = getKoin().get {
                    parametersOf(componentContext, onClickBack, onLogin, signUp)
                }
                AuthComponent.Child.SignUp(
                 signUpComponent
                )
            }

            Config.Survey -> {
                val onSendSurvey = {navigation.replaceAll(Config.Reminder)}
                val surveyComponent: SurveyComponentImpl = getKoin().get {
                    parametersOf(componentContext, onSendSurvey)
                }
                AuthComponent.Child.Survey(surveyComponent)
            }

            Config.Splash -> {
                val onAuthorization = { onAuthFinished() }
                val onClickSignUpEmail = { navigation.pushToFront(Config.SignUp) }
                val onClickLogin = { navigation.pushToFront(Config.Login) }
                val accountInfoNotComplete = { navigation.replaceCurrent(Config.Survey) }
                val splashComponent: SplashComponentImpl = getKoin().get {
                    parametersOf(
                        componentContext,
                        onAuthorization,
                        onClickSignUpEmail,
                        onClickLogin,
                        accountInfoNotComplete,
                    )
                }
                AuthComponent.Child.Splash(splashComponent)
            }

            Config.Reminder -> {
                val onFinishedAuth = { onAuthFinished() }
                val component:ReminderComponentImpl = getKoin().get{
                    parametersOf(componentContext, onFinishedAuth)
                }
                AuthComponent.Child.Reminder(component)
            }
        }
    }


    @Serializable
    sealed interface Config {

        @Serializable
        data object Login : Config

        @Serializable
        data object SignUp : Config

        @Serializable
        data object Survey : Config

        @Serializable
        data object Splash : Config

        @Serializable
        data object Reminder : Config
    }


}