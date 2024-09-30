package ru.topbun.cherry_tip.data.repository

import ru.topbun.cherry_tip.data.source.local.dataStore.Settings
import io.ktor.client.call.body
import ru.topbun.cherry_tip.data.mapper.toDto
import ru.topbun.cherry_tip.data.mapper.toEntity
import ru.topbun.cherry_tip.data.mapper.toRecipeEntityList
import ru.topbun.cherry_tip.data.source.local.getToken
import ru.topbun.cherry_tip.data.source.network.dto.recipe.CategoriesDto
import ru.topbun.cherry_tip.data.source.network.dto.recipe.RecipeDto
import ru.topbun.cherry_tip.data.source.network.service.RecipeApi
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.repository.RecipeRepository
import ru.topbun.cherry_tip.utills.codeResultWrapper
import ru.topbun.cherry_tip.utills.exceptionWrapper

class RecipeRepositoryImpl(
    private val recipeApi: RecipeApi,
    private val dataStore: Settings
) : RecipeRepository {

    override suspend fun deleteRecipe(id: Int): Unit = exceptionWrapper {
        recipeApi.deleteRecipe(id = id, token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun editRecipe(recipe: RecipeEntity): Unit = exceptionWrapper {
        recipeApi.editRecipe(recipe = recipe.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun getRecipeWithId(id: Int): RecipeEntity = exceptionWrapper {
        recipeApi.getRecipeWithId(id = id).codeResultWrapper().body<RecipeDto>().toEntity()
    }

    override suspend fun createRecipe(recipe: RecipeEntity): Unit = exceptionWrapper {
        recipeApi.createRecipe(recipe = recipe.toDto(), token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun getCategories(): CategoriesEntity = exceptionWrapper {
        recipeApi.getCategories().codeResultWrapper().body<CategoriesDto>().toEntity()
    }

    override suspend fun uploadImage(image: ByteArray): String = exceptionWrapper {
        recipeApi.uploadImage(image = image, token = dataStore.getToken()).codeResultWrapper().body<String>()
    }

    override suspend fun getRecipes(
        q: String?,
        isMyRecipe: Boolean,
        take: Int?,
        skip: Int?,
        meal: Int?,
        diet: Int?,
        preparation: Int?
    ): List<RecipeEntity> = exceptionWrapper {
        val recipes = recipeApi.getRecipes(
            token = dataStore.getToken(),
            q = q,
            isMyRecipe = isMyRecipe,
            take = take,
            skip = skip,
            meal = meal,
            diet = diet,
            preparation = preparation
        ).codeResultWrapper().body<List<RecipeDto>>()
        recipes.toRecipeEntityList()
    }
}