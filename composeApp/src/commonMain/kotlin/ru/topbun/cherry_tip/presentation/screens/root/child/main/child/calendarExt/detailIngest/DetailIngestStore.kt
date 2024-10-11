package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.MealEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.useCases.calendar.GetInfoDayUseCase
import ru.topbun.cherry_tip.domain.useCases.calendar.SetRecipeToDayUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetRecipeWithIdUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestStore.State
import ru.topbun.cherry_tip.utills.ConnectException
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.toGMTDate
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface DetailIngestStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
        data object Load : Intent
        data object ClickAddMeal : Intent
        data class CancelRecipe(val id: Int) : Intent
    }

    data class State(
        val needCalories: Int,
        val calendarRecipes: MealEntity?,
        val calendarType: CalendarType,
        val recipeList: List<RecipeEntity>,
        val calendarState: CalendarTypeState,
        val recipesState: RecipesState,
    ) {

        sealed interface CalendarTypeState {
            data object Initial : CalendarTypeState
            data object Loading : CalendarTypeState
            data class Error(val msg: String) : CalendarTypeState
            data object Result : CalendarTypeState
        }

        sealed interface RecipesState {
            data object Initial : RecipesState
            data object Loading : RecipesState
            data class Error(val msg: String) : RecipesState
            data object Result : RecipesState
        }

    }

    sealed interface Label {
        data object ClickBack : Label
        data object ClickAddMeal : Label
        data object OpenAuth : Label
    }
}

class DetailIngestStoreFactory(
    private val storeFactory: StoreFactory,
    private val getInfoDayUseCase: GetInfoDayUseCase,
    private val getRecipeWithIdUseCase: GetRecipeWithIdUseCase,
    private val setRecipeToDayUseCase: SetRecipeToDayUseCase
) {

    fun create(date: LocalDate, calendarType: CalendarType): DetailIngestStore =
        object : DetailIngestStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailIngestStore",
            initialState = State(
                needCalories = 0,
                calendarRecipes = null,
                calendarType = calendarType,
                recipeList = emptyList(),
                calendarState = State.CalendarTypeState.Initial,
                recipesState = State.RecipesState.Initial,
            ),
            bootstrapper = null,
            executorFactory = { ExecutorImpl(date, calendarType) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object OpenAuth : Action

        data object CalendarLoading : Action
        data class CalendarError(val msg: String) : Action
        data class CalendarResult(val calendarRecipes: MealEntity) : Action

        data object RecipesLoading : Action
        data class RecipesResult(val recipes: List<RecipeEntity>) : Action

        data class SetNeedCalories(val calories: Int) : Action
    }

    private sealed interface Msg {
        data object CalendarLoading : Msg
        data class CalendarError(val msg: String) : Msg
        data class CalendarResult(val calendarRecipes: MealEntity) : Msg

        data object RecipesLoading : Msg
        data class RecipesResult(val recipes: List<RecipeEntity>) : Msg

        data class SetNeedCalories(val calories: Int) : Msg
    }


    private inner class ExecutorImpl(private val date: LocalDate, private val calendarType: CalendarType) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when (action) {
                is Action.CalendarError -> dispatch(Msg.CalendarError(action.msg))
                Action.CalendarLoading -> dispatch(Msg.CalendarLoading)
                is Action.CalendarResult -> dispatch(Msg.CalendarResult(action.calendarRecipes))
                Action.RecipesLoading -> dispatch(Msg.RecipesLoading)
                is Action.RecipesResult -> dispatch(Msg.RecipesResult(action.recipes))
                is Action.SetNeedCalories -> dispatch(Msg.SetNeedCalories(action.calories))
                Action.OpenAuth -> publish(Label.OpenAuth)
            }
        }

        private suspend fun CoroutineScope.loadRecipes(calendar: MealEntity) {
            dispatch(Msg.RecipesLoading)
            val recipes = calendar.recipes.map { meal ->
                async {
                    try {
                        getRecipeWithIdUseCase(meal.recipeId).copy(mealId = meal.id)
                    } catch (e: Exception) {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
            dispatch(Msg.RecipesResult(recipes))
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            val state = state()
            when (intent) {
                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.ClickAddMeal -> publish(Label.ClickAddMeal)
                Intent.Load -> {
                    scope.launch(handlerTokenException { publish(Label.OpenAuth) }) {
                        wrapperStoreException({
                            dispatch(Msg.CalendarLoading)
                            val calendar = getInfoDayUseCase(date.toGMTDate())
                            val calories = when(calendarType){
                                CalendarType.Breakfast -> calendar.breakfast
                                CalendarType.Lunch -> calendar.lunch
                                CalendarType.Dinner -> calendar.dinner
                                CalendarType.Snack -> calendar.snack
                            }
                            dispatch(Msg.SetNeedCalories(calories))
                            val calendarRecipes = calendar.recipes.find { it.calendarType == calendarType } ?: throw ConnectException("Not found recipes")
                            println(calendarRecipes)
                            dispatch(Msg.CalendarResult(calendarRecipes))
                            loadRecipes(calendarRecipes)
                        }) {
                            dispatch(Msg.CalendarError(it))
                        }
                    }
                }
                is Intent.CancelRecipe -> {
                    scope.launch(handlerTokenException { publish(Label.OpenAuth) }) {
                        wrapperStoreException({
                            val recipes = state.recipeList
                            val newRecipes = recipes.filter { it.mealId != intent.id }
                            val recipesIds = newRecipes.map{ it.id }
                            val newCalendar = setRecipeToDayUseCase(date.toGMTDate(), calendarType, recipesIds)
                            loadRecipes(newCalendar)
                            getInfoDayUseCase(date.toGMTDate())
                            dispatch(Msg.CalendarResult(newCalendar))
                        }){
                            dispatch(Msg.CalendarError(it))
                        }
                    }
                }

            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.CalendarError -> copy(calendarState = State.CalendarTypeState.Error(message.msg))
            Msg.CalendarLoading -> copy(calendarState = State.CalendarTypeState.Loading)
            is Msg.CalendarResult -> copy(calendarState = State.CalendarTypeState.Result, calendarRecipes = message.calendarRecipes)
            Msg.RecipesLoading -> copy(recipesState = State.RecipesState.Loading)
            is Msg.RecipesResult -> copy(recipesState = State.RecipesState.Result, recipeList = message.recipes)
            is Msg.SetNeedCalories -> copy(needCalories = message.calories)
        }
    }
}

