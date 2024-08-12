package ru.topbun.cherry_tip.data.source.network.service

import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import ru.topbun.cherry_tip.data.source.network.ApiFactory
import ru.topbun.cherry_tip.data.source.network.dto.recipe.RecipeDto
import ru.topbun.cherry_tip.data.source.network.token
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity

class RecipeApi(
    private val api: ApiFactory
) {

    suspend fun deleteRecipe(id: Int, token: String) = api.client.delete("/recipe/$id") {
        token(token)
    }

    suspend fun editRecipe(recipe: RecipeDto, token: String) =
        api.client.put("/recipe/${recipe.id}") {
            setBody(recipe)
            token(token)
        }

    suspend fun getRecipeWithId(id: Int) = api.client.get("/recipe/$id")

    suspend fun createRecipe(recipe: RecipeDto, token: String) = api.client.post("/recipe/") {
        setBody(recipe)
        token(token)
    }

    suspend fun getCategories() = api.client.get("/recipe/tags")

    suspend fun getRecipes(
        q: String?,
        take: Int?,
        skip: Int?,
        category: Int?,
        diet: Int?,
        preparation: Int?
    ) = api.client.get("/recipe/search"){
        parameter(QUERY, q)
        parameter(TAKE, take)
        parameter(SKIP, skip)
        parameter(CATEGORY, category)
        parameter(DIET, diet)
        parameter(PREPARATION, preparation)
    }

    private companion object{
        const val QUERY = "q"
        const val TAKE = "take"
        const val SKIP = "skip"
        const val CATEGORY = "category"
        const val DIET = "diet"
        const val PREPARATION = "preparation"
    }

}