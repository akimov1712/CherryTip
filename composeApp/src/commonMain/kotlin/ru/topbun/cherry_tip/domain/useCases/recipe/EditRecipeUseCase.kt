package ru.topbun.cherry_tip.domain.useCases.recipe

import ru.topbun.cherry_tip.di.repositoryModule
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.repository.RecipeRepository

class EditRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(recipe: RecipeEntity) = repository.editRecipe(recipe)

}