package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.repository.UserRepository

class LogOutUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke() = repository.logOut()

}