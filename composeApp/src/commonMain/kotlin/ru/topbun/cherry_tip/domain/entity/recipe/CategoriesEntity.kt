package ru.topbun.cherry_tip.domain.entity.recipe

data class CategoriesEntity(
    val types: List<TagEntity>,
    val preparations: List<TagEntity>,
    val diets: List<TagEntity>,
)
