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
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainComponentImpl

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
                val onAuthFinished = { navigation.replaceAll(Config.Main) }
                RootComponent.Child.Auth( getKoin().get<AuthComponentImpl> {
                    parametersOf(componentContext, onAuthFinished)
                })
            }

            Config.Main -> {
                val onOpenAuth = { navigation.replaceAll(Config.Auth) }
                val component: MainComponentImpl = getKoin().get{ parametersOf(componentContext, onOpenAuth) }
                RootComponent.Child.Main(component)
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