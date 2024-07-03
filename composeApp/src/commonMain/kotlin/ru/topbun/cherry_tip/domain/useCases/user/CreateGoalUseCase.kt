package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository

class CreateGoalUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(goal: GoalEntity) = repository.createGoal(goal)

}