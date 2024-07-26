package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus

interface ChallengeRepository {

    suspend fun getChallenges(status: ChallengeStatus): List<ChallengeEntity>

}