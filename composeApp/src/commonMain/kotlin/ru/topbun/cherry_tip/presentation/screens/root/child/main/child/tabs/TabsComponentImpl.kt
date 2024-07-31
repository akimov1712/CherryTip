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
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile.ProfileComponentImpl

class TabsComponentImpl(
    private val componentContext: ComponentContext,
    private val onOpenChallenge: () -> Unit,
    private val onOpenChallengeDetail: (Int) -> Unit,
    private val onOpenAuth: () -> Unit,
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Profile,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): TabsComponent.Child = when (config) {
        Config.Home -> {
            val component: HomeComponentImpl = getKoin().get {
                parametersOf(componentContext, onOpenChallenge, onOpenChallengeDetail, onOpenAuth)
            }
            TabsComponent.Child.Home(component)
        }

        Config.Profile -> {
            val onClickAccount = {}
            val onClickProfile = {}
            val onClickGoals = {}
            val onClickUnits = {}
            val component: ProfileComponentImpl = getKoin().get {
                parametersOf(
                    componentContext,
                    onClickAccount,
                    onClickProfile,
                    onClickGoals,
                    onClickUnits
                )
            }
            TabsComponent.Child.Profile(component)
        }
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data object Home : Config

        @Serializable
        data object Profile : Config

    }
}