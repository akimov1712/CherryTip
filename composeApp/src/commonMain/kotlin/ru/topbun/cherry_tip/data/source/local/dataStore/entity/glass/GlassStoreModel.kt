package ru.topbun.cherry_tip.data.source.local.dataStore.entity.glass

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import ru.topbun.cherry_tip.utills.now

@Serializable
data class GlassStoreModel(
    val countDrinkGlass: Int,
    val countNeededGlass: Int,
    val day: Int
)
