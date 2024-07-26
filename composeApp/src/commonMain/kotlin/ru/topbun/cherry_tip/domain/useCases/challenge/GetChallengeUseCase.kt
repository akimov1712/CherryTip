package ru.topbun.cherry_tip.domain.useCases.challenge

import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.repository.ChallengeRepository

class GetChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(status: ChallengeStatus) = repository.getChallenges(status)

}