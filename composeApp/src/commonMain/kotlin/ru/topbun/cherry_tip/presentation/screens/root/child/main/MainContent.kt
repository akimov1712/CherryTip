package ru.topbun.cherry_tip.presentation.screens.root.child.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal.AppendMealScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestionScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsScreen
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.utills.setColorStatusBar

@Composable
fun MainScreen(
    component: MainComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    setColorStatusBar(Colors.White, true)
    Children(
        stack = component.stack,
        animation = stackAnimation { child -> defaultAnimationScreen }
    ) {
        when (val instance = it.instance) {
            is MainComponent.Child.Challenge -> {
                ChallengeScreen(instance.component, modifier)
            }

            is MainComponent.Child.ChallengeDetail -> {
                ChallengeDetailScreen(instance.component, modifier)
            }
            is MainComponent.Child.Tabs -> {
                TabsScreen(instance.component, modifier)
            }

            is MainComponent.Child.Account -> {
                AccountScreen(instance.component, modifier)
            }
            is MainComponent.Child.Profile -> {
                ProfileScreen(instance.component, modifier)
            }

            is MainComponent.Child.Goal -> {
                GoalScreen(instance.component, modifier)
            }

            is MainComponent.Child.Units -> {
                UnitsScreen(instance.component, modifier)
            }

            is MainComponent.Child.AddRecipe -> {
                AddRecipeScreen(instance.component, modifier)
            }

            is MainComponent.Child.DetailIngest -> {
                DetailIngestionScreen(instance.component, modifier)
            }

            is MainComponent.Child.AppendMeal -> {
                AppendMealScreen(instance.component, modifier)
            }
        }
    }

}

private val defaultAnimationScreen = slide()