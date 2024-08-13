package ru.topbun.cherry_tip.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
    private val dataStore: DataStore<Preferences>
) : RecipeRepository {

    override suspend fun deleteRecipe(id: Int): Unit = exceptionWrapper {
        recipeApi.deleteRecipe(id = id, token = dataStore.getToken()).codeResultWrapper()
    }

    override suspend fun editRecipe(recipe: RecipeEntity): Unit = exceptionWrapper {
        recipeApi.editRecipe(recipe = recipe.toDto(), token = dataStore.getToken())
    }

    override suspend fun getRecipeWithId(id: Int): RecipeEntity = exceptionWrapper {
        recipeApi.getRecipeWithId(id = id).body<RecipeDto>().toEntity()
    }

    override suspend fun createRecipe(recipe: RecipeEntity): Unit = exceptionWrapper {
        recipeApi.createRecipe(recipe = recipe.toDto(), token = dataStore.getToken())
    }

    override suspend fun getCategories(): CategoriesEntity = exceptionWrapper {
        recipeApi.getCategories().body<CategoriesDto>().toEntity()
    }

    override suspend fun getRecipes(
        q: String?,
        isMyRecipe: Boolean,
        take: Int?,
        skip: Int?,
        category: Int?,
        diet: Int?,
        preparation: Int?
    ): List<RecipeEntity> = exceptionWrapper {
        recipeApi.getRecipes(
            token = dataStore.getToken(),
            q = q,
            isMyRecipe = isMyRecipe,
            take = take,
            skip = skip,
            category = category,
            diet = diet,
            preparation = preparation
        ).body<List<RecipeDto>>().toRecipeEntityList()
    }
}