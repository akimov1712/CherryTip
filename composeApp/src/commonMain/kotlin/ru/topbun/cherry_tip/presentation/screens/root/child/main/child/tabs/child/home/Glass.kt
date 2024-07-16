package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity

data class Glass(
    val type: Type = Type.EMPTY
){
    enum class Type{
        EMPTY, ADDED, FILL
    }
}

fun GlassEntity.toConsumption() = List(this.countNeededGlass) {
    Glass(
        when{
            it < this.countDrinkGlass -> Glass.Type.FILL
            it == this.countDrinkGlass -> Glass.Type.ADDED
            else -> Glass.Type.EMPTY
        }
    )
}.toMutableList()
