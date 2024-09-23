package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.Intent.*
import ru.topbun.cherry_tip.utills.componentScope

class AddRecipeComponentImpl(
    componentContext: ComponentContext,
    private val onBack: () -> Unit,
    private val storeFactory: AddRecipeStoreFactory
) : AddRecipeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    AddRecipeStore.Label.ClickBack -> onBack()
                    AddRecipeStore.Label.RecipeAdded -> onBack()
                }
            }
        }
    }

    override fun clickBack() = store.accept(ClickBack)
    override fun addRecipe() = store.accept(AddRecipe)
    
    override fun changeTitle(name: String) = store.accept(ChangeName(name))
    override fun changeImage(image: ByteArray?) = store.accept(ChangeImage(image))
    override fun changeDescr(text: String) = store.accept(ChangeDescr(text))
    override fun changeCookingTime(time: Int?) = store.accept(ChangeCookingTime(time))
    override fun changeProtein(protein: Int?) = store.accept(ChangeProtein(protein))
    override fun changeCarbs(carbs: Int?) = store.accept(ChangeCarbs(carbs))
    override fun changeFat(fat: Int?) = store.accept(ChangeFat(fat))
    override fun changeKcal(kcal: Int?) = store.accept(ChangeKcal(kcal))
    override fun changeDifficulty(difficulty: Difficulty?) = store.accept(ChangeDifficulty(difficulty))
    override fun changeMeals(meals: TagEntity?) = store.accept(ChangeMeals(meals))
    override fun changePreparation(preparation: TagEntity?) = store.accept(ChangePreparation(preparation))
    override fun changeDiets(diets: TagEntity?) = store.accept(ChangeDiets(diets))
    override fun loadCategories() = store.accept(LoadCategories)

}