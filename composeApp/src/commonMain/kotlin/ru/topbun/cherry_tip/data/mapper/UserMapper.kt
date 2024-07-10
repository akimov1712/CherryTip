package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.user.AccountInfoDto
import ru.topbun.cherry_tip.data.source.network.dto.user.GoalDto
import ru.topbun.cherry_tip.data.source.network.dto.user.ProfileDto
import ru.topbun.cherry_tip.data.source.network.dto.user.UnitsDto
import ru.topbun.cherry_tip.domain.entity.user.AccountInfoEntity
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.utills.parseToGMTDate
import ru.topbun.cherry_tip.utills.toIso8601

fun GoalDto.toEntity() = GoalEntity(
    active = active,
    goalType = goalType,
    calorieGoal = calorieGoal
)

fun GoalEntity.toDto() = GoalDto(
    active = active,
    goalType = goalType,
)

fun ProfileDto.toEntity() = ProfileEntity(
    firstName = firstName,
    lastName = lastName,
    birth = birth.parseToGMTDate(),
    city = city,
    gender = gender,
)

fun ProfileEntity.toDto() = ProfileDto(
    firstName = firstName,
    lastName = lastName,
    birth = birth.toIso8601(),
    city = city,
    gender = gender,
)

fun UnitsDto.toEntity() = UnitsEntity(
    weight = weight,
    height = height,
    targetWeight = targetWeight,
    bloodGlucose = bloodGlucose
)

fun UnitsEntity.toDto() = UnitsDto(
    weight = weight,
    height = height,
    targetWeight = targetWeight,
    bloodGlucose = bloodGlucose
)

fun AccountInfoDto.toEntity() = AccountInfoEntity(
    id = id,
    createdAt = createdAt.parseToGMTDate(),
    updatedAt = updatedAt.parseToGMTDate(),
    email = email,
    login = login,
    role = role,
    passwordHash = passwordHash,
    profile = profile?.toEntity(),
    units = units?.toEntity(),
    goal = goal?.toEntity(),
)