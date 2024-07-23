package ru.topbun.cherry_tip.presentation.screens.root.child.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsScreen

@Composable
fun MainScreen(
    componentContext: ComponentContext,
    modifier: Modifier = Modifier
) {
//    TabsScreen(componentContext, modifier)
    ChallengeDetailScreen()
}