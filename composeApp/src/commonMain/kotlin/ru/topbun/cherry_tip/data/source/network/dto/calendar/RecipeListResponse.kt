package ru.topbun.cherry_tip.data.source.network.dto.calendar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.data.source.network.dto.recipe.RecipeDto

@Serializable
data class RecipeListResponse(
    @SerialName("id") val id: Int,
    @SerialName("recipe") val recipe: RecipeDto
)
