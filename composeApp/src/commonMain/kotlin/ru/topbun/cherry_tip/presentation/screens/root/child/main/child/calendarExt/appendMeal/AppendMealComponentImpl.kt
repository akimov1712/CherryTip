package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStoreFactory
import ru.topbun.cherry_tip.utills.componentScope

class AppendMealComponentImpl(
    componentContext: ComponentContext,
    private val date: LocalDate,
    private val calendarType: CalendarType,
    private val onClickAddRecipe: () -> Unit,
    private val onClickBack: () -> Unit,
    private val onLogOut: () -> Unit,
    private val storeFactory: AppendMealStoreFactory
) : AppendMealComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(date, calendarType) }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    AppendMealStore.Label.ClickAddRecipe -> onClickAddRecipe()
                    AppendMealStore.Label.LogOut -> onLogOut()
                    AppendMealStore.Label.ClickBack -> onClickBack()
                }
            }
        }
    }

    override fun clickBack() = store.accept(AppendMealStore.Intent.ClickBack)
    override fun appendMeal(id: Int) = store.accept(AppendMealStore.Intent.AppendMeal(id))
    override fun deleteRecipe(id: Int) = store.accept(AppendMealStore.Intent.DeleteRecipe(id))
    override fun changeQuery(query: String) = store.accept(AppendMealStore.Intent.ChangeQuery(query))
    override fun changeTab(index: Int) = store.accept(AppendMealStore.Intent.ChangeTab(index))
    override fun changeTags(meal: Int?, preparation: Int?, diets: Int?) = store.accept(AppendMealStore.Intent.ChangeTags(meal, preparation, diets))
    override fun clickAddRecipe() = store.accept(AppendMealStore.Intent.ClickAddRecipe)
    override fun loadCategory() = store.accept(AppendMealStore.Intent.LoadCategories)
    override fun loadRecipes() = store.accept(AppendMealStore.Intent.LoadRecipes)
}