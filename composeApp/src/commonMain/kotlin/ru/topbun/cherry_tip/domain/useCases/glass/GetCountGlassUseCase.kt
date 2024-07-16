package ru.topbun.cherry_tip.domain.useCases.glass

import ru.topbun.cherry_tip.domain.repository.GlassRepository

class GetCountGlassUseCase(
    private val repository: GlassRepository
) {

    suspend operator fun invoke() = repository.getCountGlass()

}