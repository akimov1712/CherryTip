package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.challenge.ChallengeDto
import ru.topbun.cherry_tip.data.source.network.dto.challenge.ChallengeStatusDto
import ru.topbun.cherry_tip.data.source.network.dto.challenge.UserChallengeDto
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.entity.challenge.UserChallengeEntity
import ru.topbun.cherry_tip.presentation.ui.utills.hexToColor
import ru.topbun.cherry_tip.utills.parseToGMTDate

fun ChallengeDto.toEntity() = ChallengeEntity(
    id = id,
    title = title,
    description = description,
    image = image,
    color = hexToColor(color),
    durationDays = durationDays,
    difficulty = difficulty,
    tips = tips,
    userChallenge = userChallenge?.toEntity()
)

fun UserChallengeDto.toEntity() = UserChallengeEntity(
    id = this.id, startDate = this.startDate.parseToGMTDate(), status = when (this.status) {
        ChallengeStatusDto.Started -> ChallengeStatus.Active
        ChallengeStatusDto.Canceled -> ChallengeStatus.Canceled
        ChallengeStatusDto.Finished -> ChallengeStatus.Finished
    }
)


fun List<ChallengeDto>.toEntityList() = map { it.toEntity() }