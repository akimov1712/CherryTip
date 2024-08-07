package ru.topbun.cherry_tip.domain.useCases.user

import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.repository.UserRepository

class UpdateGoalUseCase(
    private val repository: UserRepository
) {

    suspend operator fun invoke(goal: GoalEntity) = repository.updateGoal(goal)

}