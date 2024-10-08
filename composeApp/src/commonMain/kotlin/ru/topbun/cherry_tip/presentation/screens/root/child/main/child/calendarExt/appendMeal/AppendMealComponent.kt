package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal

import kotlinx.coroutines.flow.StateFlow

interface AppendMealComponent {

    val state: StateFlow<AppendMealStore.State>

    fun clickBack()
    fun appendMeal(id: Int)
    fun deleteRecipe(id: Int)
    fun changeQuery(query: String)
    fun changeTab(index: Int)
    fun changeTags(meal: Int?, preparation: Int?, diets: Int?)
    fun clickAddRecipe()
    fun loadCategory()
    fun loadRecipes()

}