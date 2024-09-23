package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsComponentImpl

class TabsComponentImpl(
    private val componentContext: ComponentContext,
    private val onOpenChallenge: () -> Unit,
    private val onOpenChallengeDetail: (Int) -> Unit,
    private val onOpenAuth: () -> Unit,
    private val onClickAccount: () -> Unit,
    private val onClickProfile: () -> Unit,
    private val onClickGoals: () -> Unit,
    private val onClickUnits: () -> Unit,
    private val onClickAddRecipe: () -> Unit,
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, TabsComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Recipe,
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

        Config.Settings -> {
            val component: SettingsComponentImpl = getKoin().get {
                parametersOf(
                    componentContext,
                    onClickAccount,
                    onClickProfile,
                    onClickGoals,
                    onClickUnits
                )
            }
            TabsComponent.Child.Settings(component)
        }

        Config.Recipe -> {
            val component: RecipeComponentImpl = getKoin().get{
                parametersOf(componentContext, onOpenAuth, onClickAddRecipe)
            }
            TabsComponent.Child.Recipe(component)
        }
    }

    @Serializable
    sealed interface Config {

        @Serializable data object Home : Config
        @Serializable data object Recipe : Config
        @Serializable data object Settings : Config

    }
}