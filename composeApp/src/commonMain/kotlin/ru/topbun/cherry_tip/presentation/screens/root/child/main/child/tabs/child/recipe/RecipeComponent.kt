package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import kotlinx.coroutines.flow.StateFlow

interface RecipeComponent {

    val state: StateFlow<RecipeStore.State>

    fun changeQuery(query: String)
    fun changeTab(index: Int)
    fun clickAddRecipe()


}