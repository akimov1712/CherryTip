package ru.topbun.cherry_tip.domain.useCases.auth

import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.entity.SignUpEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(signUp: SignUpEntity) = repository.singUp(signUp)

}