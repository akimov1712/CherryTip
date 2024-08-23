package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.recipe.CategoriesDto
import ru.topbun.cherry_tip.data.source.network.dto.recipe.RecipeDto
import ru.topbun.cherry_tip.data.source.network.dto.recipe.TagDto
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    createdAt = null,
    updatedAt = null,
    title = title,
    descr = descr,
    image = image,
    video = video,
    calories = calories,
    cookingTime = cookingTime,
    difficulty = difficulty,
    userId = null,
    protein = protein,
    fat = fat,
    carbs = carbs,
    categoryId = categoryId,
    dietsTypeId = dietsTypeId,
    preparationId = preparationId,
)

fun List<RecipeDto>.toRecipeEntityList() = map{ it.toEntity() }

fun RecipeDto.toEntity(): RecipeEntity{
    val nutrients = protein + fat + carbs.toFloat()
    return RecipeEntity(
        id = id,
        title = title,
        descr = descr,
        image = image,
        video = video,
        cookingTime = cookingTime,
        difficulty = difficulty,
        calories = calories,
        protein = protein,
        proteinPercent = protein / nutrients,
        fat = fat,
        fatPercent = fat / nutrients,
        carbs = carbs,
        carbsPercent = carbs / nutrients,
        categoryId = categoryId,
        dietsTypeId = dietsTypeId,
        preparationId = preparationId,
    )
}

fun CategoriesEntity.toDto() = CategoriesDto(
    types = types.toDtoList(),
    preparations = preparations.toDtoList(),
    diets = diets.toDtoList()
)

fun CategoriesDto.toEntity() = CategoriesEntity(
    types = types.toEntityList(),
    preparations = preparations.toEntityList(),
    diets = diets.toEntityList()
)

fun List<TagDto>.toEntityList() = map { it.toEntity() }
fun List<TagEntity>.toDtoList() = map { it.toDto() }

fun TagEntity.toDto() = TagDto(
    id = id,
    title = title,
    icon = icon
)

fun TagDto.toEntity() = TagEntity(
    id = id,
    title = title,
    icon = icon
)