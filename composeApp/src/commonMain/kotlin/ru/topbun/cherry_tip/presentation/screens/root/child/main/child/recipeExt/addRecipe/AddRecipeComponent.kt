package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity

interface AddRecipeComponent {

    val state: StateFlow<AddRecipeStore.State>

    fun clickBack()
    fun addRecipe()

    fun changeTitle(name: String)
    fun changeImage(image: ByteArray?)
    fun changeDescr(text: String)
    fun changeCookingTime(time: Int?)
    fun changeProtein(protein: String?)
    fun changeCarbs(carbs: String?)
    fun changeFat(fat: String?)
    fun changeDifficulty(difficulty: Difficulty?)
    fun changeMeals(meals: TagEntity?)
    fun changePreparation(preparation: TagEntity?)
    fun changeDiets(diets: TagEntity?)
    fun loadCategories()

}