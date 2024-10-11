package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarRecipeListDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.MealDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.RecipeListResponse
import ru.topbun.cherry_tip.data.source.network.dto.calendar.SetRecipeResponse
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarRecipeEntity
import ru.topbun.cherry_tip.domain.entity.calendar.MealEntity
import ru.topbun.cherry_tip.utills.parseToGMTDate

fun CalendarDto.toEntity() = CalendarEntity(
    id = this.id,
    date = this.date.parseToGMTDate(),
    goal = this.goal,
    needCalories = this.needCalories,
    protein = this.protein,
    carbs = this.carbs,
    fat = this.fat,
    breakfast = this.breakfast,
    lunch = this.lunch,
    dinner = this.dinner,
    snack = this.snack,
    userId = this.userId,
    recipes = recipes.map { it.toEntity() },
)

fun SetRecipeResponse.toEntity() = MealEntity(
    id = this.id,
    calendarType = this.calendarType,
    dayId = this.dayId,
    recipes = recipes.map { it.toEntity() }
)

fun RecipeListResponse.toEntity() = CalendarRecipeEntity(
    id = this.id,
    recipeId = this.recipe.id,
    calories = this.recipe.calories ?: 0,
    protein = this.recipe.protein.toString(),
    fat = this.recipe.fat.toString(),
    carbs = this.recipe.carbs.toString(),
)


fun MealDto.toEntity() = MealEntity(
    id = id,
    calendarType = calendarType,
    dayId = dayId,
    recipes = recipes.map { it.toEntity() },
)

fun CalendarRecipeListDto.toEntity() = CalendarRecipeEntity(
    id = this.id,
    recipeId = this.recipe.id,
    calories = this.recipe.calories,
    protein = this.recipe.protein,
    fat = this.recipe.fat,
    carbs = this.recipe.carbs,
)
