package ru.topbun.cherry_tip.presentation.screens.root.child.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.ProfileAccountScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsScreen

@Composable
fun MainScreen(
    component: MainComponent,
) {
    Children(
        stack = component.stack,
        animation = stackAnimation { child ->
            when (child.instance) {
                is MainComponent.Child.Challenge -> defaultAnimationScreen
                is MainComponent.Child.ChallengeDetail -> defaultAnimationScreen
                is MainComponent.Child.Tabs -> defaultAnimationScreen
                is MainComponent.Child.TipsDetail -> defaultAnimationScreen
                is MainComponent.Child.ProfileAccount -> defaultAnimationScreen
            }
        }
    ) {
        when (val instance = it.instance) {
            is MainComponent.Child.Challenge -> {
                ChallengeScreen(instance.component)
            }

            is MainComponent.Child.ChallengeDetail -> {
                ChallengeDetailScreen(instance.component)
            }
            is MainComponent.Child.Tabs -> {
                TabsScreen(instance.component)
            }

            is MainComponent.Child.TipsDetail -> {}
            is MainComponent.Child.ProfileAccount -> {
                ProfileAccountScreen(instance.component)
            }
        }
    }

}

private val defaultAnimationScreen = slide()