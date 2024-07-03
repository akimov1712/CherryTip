package ru.topbun.cherry_tip.domain.entity.user

import io.ktor.util.date.GMTDate
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments.gender.Gender

data class ProfileEntity(
    val firstName: String,
    val lastName: String?,
    val birth: GMTDate,
    val city: String?,
    val gender: Gender,
)
