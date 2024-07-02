package ru.topbun.cherry_tip.presentation.screens.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponentImpl

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
                val onSignUp = {}
                val loginComponent: LoginComponentImpl = getKoin().get {
                    parametersOf(componentContext, onClickBack, onSignUp)
                }
                AuthComponent.Child.Login(
                    loginComponent
                )
            }
        }
    }


    @Serializable
    sealed interface Config {

        @Serializable
        data object Login : Config
    }


}