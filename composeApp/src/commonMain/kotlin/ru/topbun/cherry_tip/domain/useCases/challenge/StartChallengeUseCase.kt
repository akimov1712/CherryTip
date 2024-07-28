package ru.topbun.cherry_tip.domain.useCases.challenge

import ru.topbun.cherry_tip.domain.repository.ChallengeRepository

class StartChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(id: Int) = repository.startChallenge(id)

}