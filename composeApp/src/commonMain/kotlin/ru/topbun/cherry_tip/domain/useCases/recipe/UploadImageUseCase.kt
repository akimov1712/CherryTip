package ru.topbun.cherry_tip.domain.useCases.recipe

import ru.topbun.cherry_tip.di.repositoryModule
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.repository.RecipeRepository

class UploadImageUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(image: ByteArray) = repository.uploadImage(image)

}