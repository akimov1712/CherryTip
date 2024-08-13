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

    override fun search(query: String) = store.accept(RecipeStore.Intent.Search(query))
    override fun changeTab(tab: RecipeTabs) = store.accept(RecipeStore.Intent.ChangeTab(tab))
    override fun clickAddRecipe() = store.accept(RecipeStore.Intent.ClickAddRecipe)
}