package ru.topbun.cherry_tip.presentation.screens.root.child.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsScreen

@Composable
fun MainScreen(
    component: MainComponent,
) {
    Children(
        stack = component.stack,
        animation = stackAnimation { child -> defaultAnimationScreen }
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
            is MainComponent.Child.Account -> {
                AccountScreen(instance.component)
            }
            is MainComponent.Child.Profile -> {
                ProfileScreen(instance.component)
            }

            is MainComponent.Child.Goal -> {
                GoalScreen(instance.component)
            }

            is MainComponent.Child.Units -> {
                UnitsScreen(instance.component)
            }
        }
    }

}

private val defaultAnimationScreen = slide()