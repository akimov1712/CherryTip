package ru.topbun.cherry_tip.domain.useCases.recipe

import ru.topbun.cherry_tip.di.repositoryModule
import ru.topbun.cherry_tip.domain.repository.RecipeRepository

class GetRecipeWithIdUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(id: Int) = repository.getRecipeWithId(id)

}