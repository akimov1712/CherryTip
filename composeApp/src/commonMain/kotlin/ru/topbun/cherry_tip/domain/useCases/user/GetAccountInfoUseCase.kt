package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository

class GetAccountInfoUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke() = repository.getAccountInfo()

}