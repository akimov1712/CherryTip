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
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailComponentImpl
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
            val openChallengeDetail = { navigation.pushToFront(Config.ChallengeDetail) }
            val component: TabsComponentImpl = getKoin().get{ parametersOf(componentContext, openChallenge, openChallengeDetail, onOpenAuth) }
            MainComponent.Child.Tabs(component)
        }
        Config.Challenge -> {
            val onClickBack = { navigation.pop() }
            val openChallengeDetail = { navigation.pushToFront(Config.ChallengeDetail) }
            val component: ChallengeComponentImpl = getKoin().get{ parametersOf(componentContext, onClickBack, openChallengeDetail) }
            MainComponent.Child.Challenge(component)
        }
        Config.ChallengeDetail -> {
            val onClickBack = {navigation.pop()}
            val component: ChallengeDetailComponentImpl = getKoin().get{ parametersOf(componentContext, onClickBack) }
            MainComponent.Child.ChallengeDetail(component)
        }
        Config.TipsDetail -> TODO()
    }

    @Serializable
    sealed interface Config{

        @Serializable
        data object Tabs: Config

        @Serializable
        data object Challenge: Config

        @Serializable
        data object ChallengeDetail: Config

        @Serializable
        data object TipsDetail: Config

    }

}