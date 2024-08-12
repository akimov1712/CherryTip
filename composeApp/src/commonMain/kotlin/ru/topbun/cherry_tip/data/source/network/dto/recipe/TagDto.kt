package ru.topbun.cherry_tip.data.source.network.dto.recipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("icon") val icon: String,
)
