package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository

class CreateProfileUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(profile: ProfileEntity) = repository.createProfile(profile)

}