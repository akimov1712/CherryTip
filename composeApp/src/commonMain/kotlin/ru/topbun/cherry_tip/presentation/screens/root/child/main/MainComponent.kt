package ru.topbun.cherry_tip.presentation.screens.root.child.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail.TipsDetailComponent

interface MainComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Tabs(val component: TabsComponent): Child
        data class Challenge(val component: ChallengeComponent): Child
        data class ChallengeDetail(val component: ChallengeDetailComponent): Child
        data class TipsDetail(val component: TipsDetailComponent): Child
    }


}