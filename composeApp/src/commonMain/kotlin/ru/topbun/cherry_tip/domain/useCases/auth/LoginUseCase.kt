package ru.topbun.cherry_tip.domain.useCases.auth

import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(login: LoginEntity) = repository.login(login)

}