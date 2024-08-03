package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.user.AccountInfoEntity
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity

interface UserRepository {

    suspend fun createProfile(profile: ProfileEntity)
    suspend fun updateProfile(profile: ProfileEntity)
    suspend fun createGoal(goal: GoalEntity)
    suspend fun updateGoal(goal: GoalEntity)
    suspend fun createUnits(units: UnitsEntity)
    suspend fun updateUnits(units: UnitsEntity)
    suspend fun getAccountInfo(): AccountInfoEntity

    suspend fun tokenIsValid()
    suspend fun checkAccountInfoComplete()
    suspend fun logOut()


}