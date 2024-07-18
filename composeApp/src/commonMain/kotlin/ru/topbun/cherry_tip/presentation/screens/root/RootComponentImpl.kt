package ru.topbun.cherry_tip.presentation.screens.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthComponentImpl

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent,  ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when(config){
            Config.Auth -> {
                val onAuthFinished = { navigation.replaceAll(Config.Main) }
                RootComponent.Child.Auth( getKoin().get<AuthComponentImpl> {
                    parametersOf(componentContext, onAuthFinished)
                })
            }

            Config.Main -> {
                RootComponent.Child.Main(componentContext)
            }
        }
    }

    @Serializable
    sealed interface Config{

        @Serializable
        data object Auth: Config

        @Serializable
        data object Main: Config

    }

}