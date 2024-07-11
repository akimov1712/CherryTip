package ru.topbun.cherry_tip.presentation.screens.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform.getKoin
import org.koin.core.parameter.parametersOf
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponent
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponentImpl

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent,  ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Auth,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when(config){
            Config.Auth -> {
                val onAuthFinished = { navigation.replaceAll(Config.Tabs) }
                RootComponent.Child.Auth( getKoin().get<AuthComponentImpl> {
                    parametersOf(componentContext, onAuthFinished)
                })
            }

            Config.Tabs -> {
                RootComponent.Child.Tabs
            }
        }
    }

    @Serializable
    sealed interface Config{

        @Serializable
        data object Auth: Config

        @Serializable
        data object Tabs: Config

    }

}