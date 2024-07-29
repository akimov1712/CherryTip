package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.entity.challenge.UserChallengeEntity

interface ChallengeRepository {

    suspend fun getChallenges(status: ChallengeStatus): List<ChallengeEntity>
    suspend fun startChallenge(id: Int): UserChallengeEntity
    suspend fun cancelChallenge(id: Int): UserChallengeEntity
    suspend fun getUserChallengeById(id: Int): ChallengeEntity

}