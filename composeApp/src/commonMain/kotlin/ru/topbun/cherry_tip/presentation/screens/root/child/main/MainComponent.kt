package ru.topbun.cherry_tip.presentation.screens.root.child.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.tipsDetail.Tips
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponent

interface MainComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Tabs(val component: TabsComponent): Child
        data class Challenge(val component: ChallengeComponent): Child
        data class ChallengeDetail(val component: ChallengeDetailComponent): Child
        data class TipsDetail(val tip: Tips): Child
        data class ProfileAccount(val component: AccountComponent): Child
    }


}