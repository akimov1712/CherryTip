package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class RecipeComponentImpl(
    componentContext: ComponentContext,
    private val onClickAddRecipe: () -> Unit,
    private val onLogOut: () -> Unit,
    private val storeFactory: RecipeStoreFactory
) : RecipeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    RecipeStore.Label.ClickAddRecipe -> onClickAddRecipe()
                    RecipeStore.Label.LogOut -> onLogOut()
                }
            }
        }
    }

    override fun changeQuery(query: String) = store.accept(RecipeStore.Intent.ChangeQuery(query))
    override fun changeTab(index: Int) = store.accept(RecipeStore.Intent.ChangeTab(index))
    override fun clickAddRecipe() = store.accept(RecipeStore.Intent.ClickAddRecipe)
    override fun loadRecipes() = store.accept(RecipeStore.Intent.LoadRecipes)
}