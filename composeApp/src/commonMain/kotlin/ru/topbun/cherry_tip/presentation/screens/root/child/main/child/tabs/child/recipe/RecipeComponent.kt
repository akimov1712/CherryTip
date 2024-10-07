package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import kotlinx.coroutines.flow.StateFlow

interface RecipeComponent {

    val state: StateFlow<RecipeStore.State>

    fun deleteRecipe(id: Int)
    fun changeQuery(query: String)
    fun changeTab(index: Int)
    fun changeTags(meal: Int?, preparation: Int?, diets: Int?)
    fun clickAddRecipe()
    fun loadCategory()
    fun loadRecipes()

}