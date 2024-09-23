package ru.topbun.cherry_tip.domain.repository

import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity

interface RecipeRepository {

    suspend fun deleteRecipe(id: Int)
    suspend fun editRecipe(recipe: RecipeEntity)
    suspend fun getRecipeWithId(id: Int): RecipeEntity
    suspend fun createRecipe(recipe: RecipeEntity)
    suspend fun getCategories(): CategoriesEntity
    suspend fun uploadImage(image: ByteArray): String
    suspend fun getRecipes(q: String?, isMyRecipe:Boolean, take: Int?, skip: Int?, category: Int?, diet: Int?, preparation: Int?): List<RecipeEntity>

}