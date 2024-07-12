package ru.topbun.cherry_tip.data.source.network.dto.user

import io.ktor.util.date.GMTDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender

@Serializable
data class ProfileDto(
    @SerialName("firstName") val firstName: String,
    @SerialName("lastName") val lastName: String?,
    @SerialName("birth") val birth: String,
    @SerialName("city") val city: String?,
    @SerialName("sex") val gender: Gender,
)
