package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.repository.UserRepository

class CheckAccountInfoCompleteUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke() = repository.checkAccountInfoComplete()

}