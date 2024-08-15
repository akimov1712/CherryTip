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
    val calories: Int?,
    val protein: Int,
    val proteinPercent: Float,
    val fat: Int,
    val fatPercent: Float,
    val carbs: Int,
    val carbsPercent: Float,
    val categoryId: Int?,
    val dietsTypeId: Int?,
    val preparationId: Int?,

)
