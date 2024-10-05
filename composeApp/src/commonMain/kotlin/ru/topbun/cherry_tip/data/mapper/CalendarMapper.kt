package ru.topbun.cherry_tip.data.mapper

import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarRecipeDto
import ru.topbun.cherry_tip.data.source.network.dto.calendar.CalendarTypeRecipeDto
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarRecipeEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarRecipeByTypeEntity
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

fun CalendarTypeRecipeDto.toEntity() = CalendarRecipeByTypeEntity(
    id = id,
    category = category,
    dayId = dayId,
    recipes = recipes.map { it.toEntity() },
)

fun CalendarRecipeDto.toEntity() = CalendarRecipeEntity(
    id = id,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs,
)