package ru.topbun.cherry_tip.domain.useCases.challenge

import ru.topbun.cherry_tip.domain.repository.ChallengeRepository

class CancelChallengeUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(id: Int) = repository.cancelChallenge(id)

}