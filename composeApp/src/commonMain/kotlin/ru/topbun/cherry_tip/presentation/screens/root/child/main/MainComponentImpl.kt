package ru.topbun.cherry_tip.presentation.screens.root.child.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform.getKoin
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponentImpl

class MainComponentImpl(
    componentContext: ComponentContext,
    private val onOpenAuth: () -> Unit
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, MainComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Tabs,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): MainComponent.Child = when(config){
        Config.Tabs -> {
            val openChallenge = { navigation.pushToFront(Config.Challenge) }
            val openChallengeDetail = {id: Int ->  navigation.pushToFront(Config.ChallengeDetail(id)) }
            val onClickAccount = { navigation.pushToFront(Config.Account) }
            val onClickProfile = { navigation.pushToFront(Config.Profile) }
            val onClickGoals = { navigation.pushToFront(Config.Goal) }
            val onClickUnits = { navigation.pushToFront(Config.Units) }
            val component: TabsComponentImpl = getKoin().get{
                parametersOf(
                    componentContext,
                    openChallenge,
                    openChallengeDetail,
                    onOpenAuth,
                    onClickAccount,
                    onClickProfile,
                    onClickGoals,
                    onClickUnits,
                )
            }
            MainComponent.Child.Tabs(component)
        }
        Config.Challenge -> {
            val onClickBack = { navigation.pop() }
            val openChallengeDetail = { id: Int -> navigation.pushToFront(Config.ChallengeDetail(id)) }
            val component: ChallengeComponentImpl = getKoin().get{ parametersOf(componentContext, onClickBack, openChallengeDetail, onOpenAuth) }
            MainComponent.Child.Challenge(component)
        }
        is Config.ChallengeDetail -> {
            val onClickBack = {navigation.pop()}
            val component: ChallengeDetailComponentImpl = getKoin().get{
                parametersOf(componentContext, onClickBack, onOpenAuth, config.id)
            }
            MainComponent.Child.ChallengeDetail(component)
        }
        Config.TipsDetail -> TODO()
        Config.Account -> {
            val onClickBack = {navigation.pop()}
            val component: AccountComponentImpl = getKoin().get {
                parametersOf(componentContext, onOpenAuth, onClickBack)
            }
            MainComponent.Child.Account(component)
        }

        Config.Profile -> {
            val onClickBack = {navigation.pop()}
            val component: ProfileComponentImpl = getKoin().get {
                parametersOf(componentContext, onOpenAuth, onClickBack)
            }
            MainComponent.Child.Profile(component)
        }

        Config.Goal -> {
            val onClickBack = {navigation.pop()}
            val component: GoalComponentImpl = getKoin().get {
                parametersOf(componentContext, onOpenAuth, onClickBack)
            }
            MainComponent.Child.Goal(component)
        }

        Config.Units -> {
            val onClickBack = {navigation.pop()}
            val component: UnitsComponentImpl = getKoin().get {
                parametersOf(componentContext, onOpenAuth, onClickBack)
            }
            MainComponent.Child.Units(component)
        }
    }

    @Serializable
    sealed interface Config{

        @Serializable data object Tabs: Config
        @Serializable data object Challenge: Config
        @Serializable data class ChallengeDetail(val id: Int): Config
        @Serializable data object TipsDetail: Config
        @Serializable data object Account: Config
        @Serializable data object Profile: Config
        @Serializable data object Goal: Config
        @Serializable data object Units: Config
    }

}