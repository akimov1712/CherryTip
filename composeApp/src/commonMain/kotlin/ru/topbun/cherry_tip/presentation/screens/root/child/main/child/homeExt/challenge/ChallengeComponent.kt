package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge

import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus

interface ChallengeComponent {

    val state: StateFlow<ChallengeStore.State>

    fun clickBack()
    fun openChallengeDetail(id: Int)
    fun loadChallenge(status: ChallengeStatus)
    fun choiceChallengeStatus(index: Int)

}