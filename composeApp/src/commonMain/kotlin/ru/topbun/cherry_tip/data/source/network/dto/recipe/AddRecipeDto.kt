package ru.topbun.cherry_tip.data.source.network.dto.recipe

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.domain.entity.Difficulty


@Serializable
data class AddRecipeDto(
    @SerialName("createdAt") val createdAt: String?,
    @SerialName("updatedAt") val updatedAt: String?,
    @SerialName("title") val title: String,
    @SerialName("description") val descr: String?,
    @SerialName("image") val image: String,
    @SerialName("video") val video: String?,
    @SerialName("calories") val calories: Int?,
    @SerialName("cookingTime") val cookingTime: Int?,
    @SerialName("difficulty") val difficulty: Difficulty?,
    @SerialName("protein") val protein: Float,
    @SerialName("fat") val fat: Float,
    @SerialName("carbs") val carbs: Float,
    @SerialName("categoryId") val categoryId: Int?,
    @SerialName("dietsTypeId") val dietsTypeId: Int?,
    @SerialName("preparationId") val preparationId: Int?,
)
