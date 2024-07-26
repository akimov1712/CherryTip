package ru.topbun.cherry_tip.data.mapper

import androidx.compose.ui.graphics.Color
import ru.topbun.cherry_tip.data.source.network.dto.challenge.ChallengeDto
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.presentation.ui.utills.hexToColor

fun ChallengeDto.toEntity() = ChallengeEntity(
    id = id,
    title = title,
    description = description,
    image = image,
    color = hexToColor(color),
    durationDays = durationDays,
    difficulty = difficulty,
    tips = tips
)

fun List<ChallengeDto>.toEntityList() = map { it.toEntity() }