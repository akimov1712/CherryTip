package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.useCases.recipe.GetRecipesUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface RecipeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeTab(val index: Int): Intent
        data class ChangeQuery(val q: String): Intent
        data object ClickAddRecipe: Intent
    }

    data class State(
        val tabs: List<RecipeTabs>,
        val selectedIndex: Int,
        val query: String,
        val recipes: List<RecipeEntity>,
        val recipeState: RecipeState
    ){
        sealed interface RecipeState{
            data object Initial: RecipeState
            data object Loading: RecipeState
            data class Error(val text: String): RecipeState
            data object Result: RecipeState
        }
    }

    sealed interface Label {
        data object ClickAddRecipe: Label
        data object LogOut: Label
    }
}

class RecipeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getRecipesUseCase: GetRecipesUseCase
) {

    fun create(): RecipeStore =
        object : RecipeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "RecipeStore",
            initialState = State(
                tabs = RecipeTabs.entries,
                selectedIndex = 0,
                query = "",
                recipes = emptyList(),
                recipeState = State.RecipeState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object RecipeStateLoading: Action
        data class RecipeStateError(val text: String): Action
        data class RecipeStateResult(val recipes: List<RecipeEntity>): Action

        data object LogOut: Action
    }

    private sealed interface Msg {
        data object RecipeStateLoading: Msg
        data class RecipeStateError(val text: String): Msg
        data class RecipeStateResult(val recipes: List<RecipeEntity>): Msg
        data class ChangeTab(val index: Int): Msg
        data class ChangeQuery(val query: String): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.RecipeStateLoading)
                    val result = getRecipesUseCase()
                    dispatch(Action.RecipeStateResult(result))
                }){
                    dispatch(Action.RecipeStateError(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                Action.LogOut -> publish(Label.LogOut)
                is Action.RecipeStateError -> dispatch(Msg.RecipeStateError(action.text))
                Action.RecipeStateLoading -> dispatch(Msg.RecipeStateLoading)
                is Action.RecipeStateResult -> dispatch(Msg.RecipeStateResult(action.recipes))
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            var searchJob: Job? = null

            val state = state()
            when(intent){
                is Intent.ChangeTab -> dispatch(Msg.ChangeTab(intent.index))
                Intent.ClickAddRecipe -> publish(Label.ClickAddRecipe)
                is Intent.ChangeQuery -> {
                    searchJob?.cancel()
                    searchJob = scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        dispatch(Msg.ChangeQuery(intent.q))
                        wrapperStoreException({
                            dispatch(Msg.RecipeStateLoading)
                            val isMyRecipe = state.tabs[state.selectedIndex] == RecipeTabs.MyRecipes
                            val result = getRecipesUseCase(q = intent.q, isMyRecipe = isMyRecipe, skip = state.recipes.size)
                            dispatch(Msg.RecipeStateResult(result))
                        }){
                            dispatch(Msg.RecipeStateError(it))
                        }
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
            is Msg.RecipeStateResult -> copy(recipes = recipes + message.recipes,recipeState = State.RecipeState.Result)
            is Msg.ChangeQuery -> copy(query = message.query)
        }
    }
}
