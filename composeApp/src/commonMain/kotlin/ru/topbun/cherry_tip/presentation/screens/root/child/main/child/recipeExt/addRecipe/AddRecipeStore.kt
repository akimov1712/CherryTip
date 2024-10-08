package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.domain.useCases.recipe.CreateRecipeUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.GetCategoriesUseCase
import ru.topbun.cherry_tip.domain.useCases.recipe.UploadImageUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.State
import ru.topbun.cherry_tip.utills.Const.BASE_URL
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.toStringOrBlank
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface AddRecipeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent

        data object LoadCategories : Intent
        data object AddRecipe : Intent

        data class ChangeImage(val image: ByteArray?) : Intent
        data class ChangeName(val text: String) : Intent
        data class ChangeDescr(val text: String) : Intent
        data class ChangeCookingTime(val time: Int?) : Intent
        data class ChangeProtein(val protein: String?) : Intent
        data class ChangeCarbs(val carbs: String?) : Intent
        data class ChangeFat(val fat: String?) : Intent
        data class ChangeDifficulty(val difficulty: Difficulty?) : Intent
        data class ChangeMeals(val meals: TagEntity?) : Intent
        data class ChangePreparation(val preparation: TagEntity?) : Intent
        data class ChangeDiets(val diets: TagEntity?) : Intent
    }

    data class State(
        val image: ByteArray?,
        val imageIsError: Boolean = false,
        val title: String,
        val titleIsError: Boolean = false,
        val descr: String,
        val cookingTime: Int?,
        val protein: String?,
        val proteinIsError: Boolean = false,
        val carbs: String?,
        val carbsIsError: Boolean = false,
        val fat: String?,
        val fatIsError: Boolean = false,
        val kcal: Int?,
        val kcalIsError: Boolean = false,
        val difficulty: Difficulty?,
        val meals: TagEntity?,
        val preparation: TagEntity?,
        val diets: TagEntity?,
        val screenState: RecipeAddedState,
        val choiceTagState: ChoiceTagState
    ) {

        sealed interface RecipeAddedState {
            data object Initial : RecipeAddedState
            data class Error(val message: String) : RecipeAddedState
            data object Loading : RecipeAddedState
        }

        sealed interface ChoiceTagState {
            data object Initial : ChoiceTagState
            data object Error : ChoiceTagState
            data object Loading : ChoiceTagState
            data class Result(val categories: CategoriesEntity) : ChoiceTagState
        }

    }

    sealed interface Label {
        data object ClickBack : Label
        data object RecipeAdded : Label
    }
}

class AddRecipeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val createRecipeUseCase: CreateRecipeUseCase,
) {

    fun create(): AddRecipeStore =
        object : AddRecipeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AddRecipeStore",
            initialState = State(
                image = null,
                title = "",
                descr = "",
                cookingTime = null,
                protein = null,
                carbs = null,
                fat = null,
                kcal = null,
                difficulty = null,
                meals = null,
                preparation = null,
                diets = null,
                screenState = State.RecipeAddedState.Initial,
                choiceTagState = State.ChoiceTagState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object ChoiceTagLoading : Action
        data class ChoiceTagResult(val categories: CategoriesEntity) : Action
        data object ChoiceTagError : Action
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
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
        data class ChangeImage(val image: ByteArray?) : Msg
        data class ChangeImageError(val value: Boolean) : Msg

        data class ChangeName(val text: String) : Msg
        data class ChangeNameError(val value: Boolean) : Msg

        data class ChangeDescr(val text: String) : Msg

        data class ChangeCookingTime(val time: Int?) : Msg

        data class ChangeProtein(val protein: String?) : Msg
        data class ChangeProteinError(val value: Boolean) : Msg

        data class ChangeCarbs(val carbs: String?) : Msg
        data class ChangeCarbsError(val value: Boolean) : Msg

        data class ChangeFat(val fat: String?) : Msg
        data class ChangeFatError(val value: Boolean) : Msg

        data class ChangeKcalError(val value: Boolean) : Msg

        data class ChangeDifficulty(val difficulty: Difficulty?) : Msg

        data class ChangeMeals(val meals: TagEntity?) : Msg

        data class ChangePreparation(val preparation: TagEntity?) : Msg

        data class ChangeDiets(val diets: TagEntity?) : Msg

        data object ScreenStateLoading : Msg
        data class ScreenStateError(val msg: String) : Msg


        data object ChoiceTagLoading : Msg
        data class ChoiceTagResult(val categories: CategoriesEntity) : Msg
        data object ChoiceTagError : Msg
    }

    private inner class  ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                Action.ChoiceTagError -> dispatch(Msg.ChoiceTagError)
                Action.ChoiceTagLoading -> dispatch(Msg.ChoiceTagLoading)
                is Action.ChoiceTagResult -> dispatch(Msg.ChoiceTagResult(action.categories))
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            var job: Job? = null
            when (intent) {
                is Intent.ChangeImage -> {
                    val image = intent.image
                    dispatch(Msg.ChangeImage(image))
                    dispatch(Msg.ChangeImageError(image == null))
                }

                is Intent.ChangeName -> {
                    val text = intent.text
                    dispatch(Msg.ChangeName(text))
                    dispatch(Msg.ChangeNameError(text.length !in (1..40)))
                }

                is Intent.ChangeDescr -> dispatch(Msg.ChangeDescr(intent.text))
                is Intent.ChangeCookingTime -> dispatch(Msg.ChangeCookingTime(intent.time))
                is Intent.ChangeProtein -> {
                    val protein = if (intent.protein?.isBlank() == true) null else intent.protein
                    dispatch(Msg.ChangeProtein(protein))
                    dispatch(Msg.ChangeProteinError(protein == null))
                }

                is Intent.ChangeCarbs -> {
                    val carbs = if (intent.carbs?.isBlank() == true) null else intent.carbs
                    dispatch(Msg.ChangeCarbs(carbs))
                    dispatch(Msg.ChangeCarbsError(carbs == null))
                }

                is Intent.ChangeFat -> {
                    val fat = if (intent.fat?.isBlank() == true) null else intent.fat
                    dispatch(Msg.ChangeFat(fat))
                    dispatch(Msg.ChangeFatError(fat == null))
                }

                Intent.LoadCategories -> {
                    scope.launch {
                        wrapperStoreException({
                            dispatch(Msg.ChoiceTagLoading)
                            val categories = getCategoriesUseCase()
                            dispatch(Msg.ChoiceTagResult(categories))
                        }){
                            dispatch(Msg.ChoiceTagError)
                        }
                    }
                }

                Intent.ClickBack -> publish(Label.ClickBack)
                is Intent.ChangeDifficulty -> dispatch(Msg.ChangeDifficulty(intent.difficulty))
                is Intent.ChangeMeals -> dispatch(Msg.ChangeMeals(intent.meals))
                is Intent.ChangePreparation -> dispatch(Msg.ChangePreparation(intent.preparation))
                is Intent.ChangeDiets -> dispatch(Msg.ChangeDiets(intent.diets))
                Intent.AddRecipe -> {
                    val state = state()
                    job?.cancel()
                    job = scope.launch(handlerTokenException { publish(Label.ClickBack) }) {
                        wrapperStoreException({
                            if(state.image == null) {
                                dispatch(Msg.ScreenStateError("Set the image"))
                                return@wrapperStoreException
                            }
                            val fieldsError = listOf(state.title, state.protein, state.kcal, state.fat, state.carbs)
                            if(fieldsError.contains(null) || fieldsError.contains("")) {
                                dispatch(Msg.ScreenStateError("Check fields for content"))
                                return@wrapperStoreException
                            }
                            dispatch(Msg.ScreenStateLoading)
                            val link = uploadImageUseCase(state.image)
                            val recipe = RecipeEntity(
                                id = -1,
                                title = state.title.trim(),
                                descr = state.descr.trim(),
                                image = BASE_URL + link.drop(1),
                                video = null,
                                cookingTime = state.cookingTime,
                                difficulty = state.difficulty,
                                calories = state.kcal,
                                protein = state.protein?.toFloat() ?: 0f,
                                proteinPercent = 0f,
                                fat = state.fat?.toFloat() ?: 0f,
                                fatPercent = 0f,
                                carbs = state.carbs?.toFloat() ?: 0f,
                                carbsPercent = 0f,
                                categoryId = state.meals?.id,
                                dietsTypeId = state.diets?.id,
                                preparationId = state.preparation?.id,
                                userId = null
                            )
                            println(
                                state.meals?.id.toStringOrBlank() +
                                state.diets?.id.toStringOrBlank() +
                                state.preparation?.id.toStringOrBlank()
                            )
                            createRecipeUseCase(recipe)
                            publish(Label.RecipeAdded)
                        }){
                            dispatch(Msg.ScreenStateError(it))
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {

        private fun calculateKcal(protein: Float?, carbs: Float?, fat: Float?): Int?{
            return if (listOf(protein,carbs, fat).contains(null)) null
                else  (4 * protein!! + 4 * carbs!! + 9 * fat!!).toInt()
        }

        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeCarbs -> copy(carbs = message.carbs, kcal = calculateKcal(protein?.toFloatOrNull(), message.carbs?.toFloatOrNull(), fat?.toFloatOrNull()))
            is Msg.ChangeCarbsError -> copy(carbsIsError = message.value)
            is Msg.ChangeCookingTime -> copy(cookingTime = message.time)
            is Msg.ChangeDescr -> copy(descr = message.text)
            is Msg.ChangeDiets -> copy(diets = message.diets)
            is Msg.ChangeDifficulty -> copy(difficulty = message.difficulty)
            is Msg.ChangeFat -> copy(fat = message.fat, kcal = calculateKcal(protein?.toFloatOrNull(), carbs?.toFloatOrNull(), message.fat?.toFloatOrNull()))
            is Msg.ChangeFatError -> copy(fatIsError = message.value)
            is Msg.ChangeImage -> copy(image = message.image)
            is Msg.ChangeImageError -> copy(imageIsError = message.value)
            is Msg.ChangeKcalError -> copy(kcalIsError = message.value)
            is Msg.ChangeMeals -> copy(meals = message.meals)
            is Msg.ChangeName -> copy(title = message.text)
            is Msg.ChangeNameError -> copy(titleIsError = message.value)
            is Msg.ChangePreparation -> copy(preparation = message.preparation)
            is Msg.ChangeProtein -> copy(protein = message.protein, kcal = calculateKcal(message.protein?.toFloatOrNull(), carbs?.toFloatOrNull(), fat?.toFloatOrNull()))
            is Msg.ChangeProteinError -> copy(proteinIsError = message.value)
            is Msg.ScreenStateError -> copy(screenState = State.RecipeAddedState.Error(message.msg))
            Msg.ScreenStateLoading -> copy(screenState = State.RecipeAddedState.Loading)
            Msg.ChoiceTagError -> copy(choiceTagState = State.ChoiceTagState.Error)
            Msg.ChoiceTagLoading -> copy(choiceTagState = State.ChoiceTagState.Loading)
            is Msg.ChoiceTagResult -> copy(choiceTagState = State.ChoiceTagState.Result(message.categories))
        }
    }


}
