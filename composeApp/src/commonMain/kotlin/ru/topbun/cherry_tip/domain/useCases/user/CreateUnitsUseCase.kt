package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository

class CreateUnitsUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(units: UnitsEntity) = repository.createUnits(units)

}