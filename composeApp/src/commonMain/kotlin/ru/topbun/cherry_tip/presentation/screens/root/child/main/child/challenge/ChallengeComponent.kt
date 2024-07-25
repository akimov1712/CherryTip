package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import kotlinx.coroutines.flow.StateFlow

interface ChallengeComponent {

    val state: StateFlow<ChallengeStore.State>

    fun clickBack()
    fun openChallengeDetail()

}