package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail

import kotlinx.coroutines.flow.StateFlow

interface ChallengeDetailComponent {

    val state: StateFlow<ChallengeDetailStore.State>

    fun clickBack()

}