package ru.topbun.cherry_tip.domain.entity.recipe

import ru.topbun.cherry_tip.domain.entity.Difficulty

data class RecipeEntity(
    val id: Int,
    val title: String,
    val descr: String?,
    val image: String,
    val video: String?,
    val cookingTime: Int?,
    val difficulty: Difficulty?,
    val userId: String?,
    val calories: Int?,
    val protein: Float,
    val proteinPercent: Float,
    val fat: Float,
    val fatPercent: Float,
    val carbs: Float,
    val carbsPercent: Float,
    val categoryId: Int?,
    val dietsTypeId: Int?,
    val preparationId: Int?,

)
