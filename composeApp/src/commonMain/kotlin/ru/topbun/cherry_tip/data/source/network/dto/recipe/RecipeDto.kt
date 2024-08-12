package ru.topbun.cherry_tip.data.source.network.dto.recipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.Difficulty

@Serializable
data class RecipeDto(
    @SerialName("") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val descr: String?,
    @SerialName("image") val image: String,
    @SerialName("video") val video: String?,
    @SerialName("cookingTime") val cookingTime: Int?,
    @SerialName("difficulty") val difficulty: Difficulty?,
    @SerialName("protein") val protein: Int,
    @SerialName("fat") val fat: Int,
    @SerialName("carbs") val carbs: Int,
    @SerialName("categoryId") val categoryId: Int?,
    @SerialName("dietsTypeId") val dietsTypeId: Int?,
    @SerialName("preparationId") val preparationId: Int?,
)
