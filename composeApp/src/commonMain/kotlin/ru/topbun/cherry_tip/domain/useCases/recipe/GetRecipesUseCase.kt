package ru.topbun.cherry_tip.domain.useCases.recipe

import ru.topbun.cherry_tip.di.repositoryModule
import ru.topbun.cherry_tip.domain.repository.RecipeRepository

class GetRecipesUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(
        q: String? = null,
        isMyRecipe:Boolean = false,
        take: Int? = null,
        skip: Int? = null,
        meal: Int? = null,
        diet: Int? = null,
        preparation: Int? = null
    ) = repository.getRecipes(q, isMyRecipe, take, skip, meal, diet, preparation)

}