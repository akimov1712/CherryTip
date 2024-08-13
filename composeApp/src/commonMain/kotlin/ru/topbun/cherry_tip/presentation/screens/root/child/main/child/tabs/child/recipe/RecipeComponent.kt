package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import kotlinx.coroutines.flow.StateFlow

interface RecipeComponent {

    val state: StateFlow<RecipeStore.State>

    fun search(query: String)
    fun changeTab(tab: RecipeTabs)
    fun clickAddRecipe()


}