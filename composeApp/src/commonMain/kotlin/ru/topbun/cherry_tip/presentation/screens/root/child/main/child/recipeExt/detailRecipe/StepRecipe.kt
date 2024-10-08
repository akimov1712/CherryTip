package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.detailRecipe

import kotlinx.serialization.Serializable

@Serializable
data class StepRecipe(
    val text: String,
    val image: String?
)
