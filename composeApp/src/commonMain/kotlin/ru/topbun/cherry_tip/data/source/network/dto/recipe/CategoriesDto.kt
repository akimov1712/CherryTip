package ru.topbun.cherry_tip.data.source.network.dto.recipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDto(
    @SerialName("categories") val types: List<TagDto>,
    @SerialName("preparations") val preparations: List<TagDto>,
    @SerialName("diets") val diets: List<TagDto>,
)
