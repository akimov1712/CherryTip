package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainComponentImpl.Config
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl

class TabsComponentImpl(
    private val componentContext: ComponentContext,
    private val onOpenChallenge: () -> Unit
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TabsComponent.Child = when (config) {
        Config.Home -> {
            val component: HomeComponentImpl = getKoin().get { parametersOf(componentContext, onOpenChallenge) }
            TabsComponent.Child.Home(component)
        }
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data object Home : Config

    }
}