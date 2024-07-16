package ru.topbun.cherry_tip.domain.useCases.glass

import ru.topbun.cherry_tip.domain.repository.GlassRepository

class AddDrinkGlassUseCase(
    private val repository: GlassRepository
) {

    suspend operator fun invoke() = repository.addDrinkGlass()

}