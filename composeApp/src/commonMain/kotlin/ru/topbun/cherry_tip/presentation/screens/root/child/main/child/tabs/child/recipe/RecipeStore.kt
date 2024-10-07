package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.useCases.recipe.DeleteRecipeUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetCategoriesUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetRecipesUseCase
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.State.ChoiceTagState
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface RecipeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object LoadCategories : Intent
        data class ChangeTab(val index: Int) : Intent
        data class ChangeQuery(val q: String) : Intent
        data class ChangeTags(val meal: Int?, val preparations: Int?, val diets: Int?) : Intent
        data object LoadRecipes : Intent
        data class DeleteRecipe(val id: Int) : Intent
        data object ClickAddRecipe : Intent
    }

    data class State(
        val userId: String,
        val tabs: List<RecipeTabs>,
        val meal: Int?,
        val preparations: Int?,
        val diets: Int?,
        val selectedIndex: Int,
        val query: String,
        val recipes: List<RecipeEntity>,
        val isEndList: Boolean,
        val recipeState: RecipeState,
        val choiceTagState: ChoiceTagState,
        val deleteRecipeState: DeleteRecipeState
    ) {
        sealed interface RecipeState {
            data object Initial : RecipeState
            data object Loading : RecipeState
            data class Error(val text: String) : RecipeState
            data object Result : RecipeState
        }

        sealed interface DeleteRecipeState {
            data object Initial : DeleteRecipeState
            data object Error : DeleteRecipeState
            data object Loading : DeleteRecipeState
            data object Result : DeleteRecipeState
        }

        sealed interface ChoiceTagState {
            data object Initial : ChoiceTagState
            data object Error : ChoiceTagState
            data object Loading : ChoiceTagState
            data class Result(val categories: CategoriesEntity) : ChoiceTagState
        }
    }

    sealed interface Label {
        data object ClickAddRecipe : Label
        data object LogOut : Label
    }
}

class RecipeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) {

    fun create(): RecipeStore =
        object : RecipeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "RecipeStore",
            initialState = State(
                userId = "",
                tabs = RecipeTabs.entries,
                meal = null,
                preparations = null,
                diets = null,
                selectedIndex = 0,
                query = "",
                recipes = emptyList(),
                isEndList = false,
                recipeState = State.RecipeState.Initial,
                choiceTagState = State.ChoiceTagState.Initial,
                deleteRecipeState = State.DeleteRecipeState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object RecipeStateLoading : Action
        data class RecipeStateError(val text: String) : Action
        data class RecipeStateResult(val recipes: List<RecipeEntity>) : Action

        data object ChoiceTagLoading : Action
        data class ChoiceTagResult(val categories: CategoriesEntity) : Action
        data object ChoiceTagError : Action

        data class SetUserId(val userId: String) : Action

        data object LogOut : Action
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    val userId = getAccountInfoUseCase().id
                    dispatch(Action.SetUserId(userId))
                }) {

                }
            }
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.ChoiceTagLoading)
                    val categories = getCategoriesUseCase()
                    dispatch(Action.ChoiceTagResult(categories))
                }) {
                    dispatch(Action.ChoiceTagError)
                }
            }
        }

    }

    private sealed interface Msg {
        data class SetRecipeList(val list: List<RecipeEntity>) : Msg

        data object RecipeStateLoading : Msg
        data class RecipeStateError(val text: String) : Msg
        data class RecipeStateResult(val recipes: List<RecipeEntity>) : Msg
        data class ChangeTab(val index: Int) : Msg
        data class ChangeTags(val meal: Int?, val preparations: Int?, val diets: Int?) : Msg
        data class ChangeQuery(val query: String) : Msg
        data class ChangeIsEndList(val value: Boolean) : Msg
        data object ClearRecipes : Msg

        data object ChoiceTagLoading : Msg
        data class ChoiceTagResult(val categories: CategoriesEntity) : Msg
        data object ChoiceTagError : Msg

        data object DeleteRecipeLoading : Msg
        data object DeleteRecipeResult : Msg
        data object DeleteRecipeError : Msg

        data class SetUserId(val userId: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when (action) {
                Action.LogOut -> publish(Label.LogOut)
                is Action.RecipeStateError -> dispatch(Msg.RecipeStateError(action.text))
                Action.RecipeStateLoading -> dispatch(Msg.RecipeStateLoading)
                is Action.RecipeStateResult -> dispatch(Msg.RecipeStateResult(action.recipes))
                Action.ChoiceTagError -> dispatch(Msg.ChoiceTagError)
                Action.ChoiceTagLoading -> dispatch(Msg.ChoiceTagLoading)
                is Action.ChoiceTagResult -> dispatch(Msg.ChoiceTagResult(action.categories))
                is Action.SetUserId -> dispatch(Msg.SetUserId(action.userId))
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            var searchJob: Job? = null
            var deleteJob: Job? = null
            val state = state()
            when (intent) {
                Intent.LoadCategories -> {
                scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                    wrapperStoreException({
                        dispatch(Msg.ChoiceTagLoading)
                        val categories = getCategoriesUseCase()
                        dispatch(Msg.ChoiceTagResult(categories))
                    }){
                        dispatch(Msg.ChoiceTagError)
                    }
                }
            }
                Intent.ClickAddRecipe -> publish(Label.ClickAddRecipe)
                Intent.LoadRecipes -> {
                    searchJob?.cancel()
                    searchJob = scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        wrapperStoreException({
                            dispatch(Msg.RecipeStateLoading)
                            val result = getRecipesUseCase(
                                q = state.query,
                                isMyRecipe = state.tabs[state.selectedIndex] == RecipeTabs.MyRecipes,
                                skip = state.recipes.size,
                                meal = state.meal,
                                preparation = state.preparations,
                                diet = state.diets
                            )
                            if (result.isEmpty()) dispatch(Msg.ChangeIsEndList(true))
                            dispatch(Msg.RecipeStateResult(result))
                        }) {
                            dispatch(Msg.RecipeStateError(it))
                        }
                    }
                }

                is Intent.ChangeQuery -> {
                    dispatch(Msg.ClearRecipes)
                    dispatch(Msg.ChangeQuery(intent.q))
                }

                is Intent.ChangeTab -> {
                    dispatch(Msg.ClearRecipes)
                    dispatch(Msg.ChangeTab(intent.index))
                }

                is Intent.ChangeTags -> {
                    dispatch(Msg.ClearRecipes)
                    dispatch(Msg.ChangeTags(intent.meal, intent.preparations, intent.diets))
                }
                is Intent.DeleteRecipe -> {
                    deleteJob?.cancel()
                    deleteJob = scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        wrapperStoreException({
                            dispatch(Msg.DeleteRecipeLoading)
                            deleteRecipeUseCase(intent.id)
                            val newRecipes = state.recipes.filter { it.id != intent.id }
                            dispatch(Msg.SetRecipeList(newRecipes))
                            dispatch(Msg.DeleteRecipeResult)
                        }) { dispatch(Msg.DeleteRecipeError) }
                    }
                }
            }
        }

    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeTab -> copy(selectedIndex = message.index)
            is Msg.RecipeStateError -> copy(recipeState = State.RecipeState.Error(message.text))
            Msg.RecipeStateLoading -> copy(recipeState = State.RecipeState.Loading)
            is Msg.RecipeStateResult -> copy(
                recipes = recipes + message.recipes,
                recipeState = State.RecipeState.Result
            )
            Msg.ChoiceTagError -> copy(choiceTagState = State.ChoiceTagState.Error)
            Msg.ChoiceTagLoading -> copy(choiceTagState = State.ChoiceTagState.Loading)
            is Msg.ChoiceTagResult -> copy(choiceTagState = State.ChoiceTagState.Result(message.categories))
            is Msg.ChangeQuery -> copy(query = message.query)
            Msg.ClearRecipes -> copy(recipes = emptyList(), isEndList = false)
            is Msg.ChangeIsEndList -> copy(isEndList = message.value)
            is Msg.ChangeTags -> copy(
                meal = message.meal,
                preparations = message.preparations,
                diets = message.diets
            )
            is Msg.SetUserId -> copy(userId = message.userId)
            Msg.DeleteRecipeError -> copy(deleteRecipeState = State.DeleteRecipeState.Error)
            Msg.DeleteRecipeLoading -> copy(deleteRecipeState = State.DeleteRecipeState.Loading)
            Msg.DeleteRecipeResult -> copy(deleteRecipeState = State.DeleteRecipeState.Result)
            is Msg.SetRecipeList -> copy(recipes = message.list)
        }
    }
}
