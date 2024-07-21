package ru.topbun.cherry_tip.data.source.local.dataStore.entity.glass

import kotlinx.serialization.Serializable

@Serializable
data class GlassStoreModel(
    val countDrinkGlass: Int,
    val countNeededGlass: Int,
    val day: Int
)
