package ru.topbun.cherry_tip.domain.useCases.challenge

import ru.topbun.cherry_tip.domain.repository.ChallengeRepository

class GetUserChallengeByIdUseCase(
    private val repository: ChallengeRepository
) {

    suspend operator fun invoke(id: Int) = repository.getUserChallengeById(id)

}