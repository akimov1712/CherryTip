package ru.topbun.cherry_tip.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity

interface GlassRepository {

    suspend fun getCountGlass(): Flow<GlassEntity>
    suspend fun addDrinkGlass()

}