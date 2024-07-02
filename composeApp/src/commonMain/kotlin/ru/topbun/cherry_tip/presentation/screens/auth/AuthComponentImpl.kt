package ru.topbun.cherry_tip.presentation.screens.auth

import androidx.compose.ui.input.key.Key.Companion.T
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpComponentImpl
import kotlin.math.sign

class AuthComponentImpl(
    componentContext: ComponentContext
): AuthComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, AuthComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Login,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config, componentContext: ComponentContext
    ): AuthComponent.Child{
        return when(config){
            Config.Login -> {
                val onClickBack = {
                    navigation.pop()
                }
                val onSignUp = { navigation.replaceCurrent(Config.SignUp) }
                val loginComponent: LoginComponentImpl = getKoin().get {
                    parametersOf(componentContext, onClickBack, onSignUp)
                }
                AuthComponent.Child.Login(
                    loginComponent
                )
            }

            Config.SignUp -> {
                val onClickBack = {
                    navigation.pop()
                }
                val onLogin = { navigation.replaceCurrent(Config.Login) }
                val signUpComponent: SignUpComponentImpl = getKoin().get {
                    parametersOf(componentContext, onClickBack, onLogin)
                }
                AuthComponent.Child.SignUp(
                 signUpComponent
                )
            }
        }
    }


    @Serializable
    sealed interface Config {

        @Serializable
        data object Login : Config


        @Serializable
        data object SignUp : Config
    }


}