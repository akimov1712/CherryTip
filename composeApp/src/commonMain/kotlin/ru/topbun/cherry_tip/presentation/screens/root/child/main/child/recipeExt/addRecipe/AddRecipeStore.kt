package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.ui.graphics.ImageBitmap
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.recipe_add_error_carbs
import cherrytip.composeapp.generated.resources.recipe_add_error_fat
import cherrytip.composeapp.generated.resources.recipe_add_error_image
import cherrytip.composeapp.generated.resources.recipe_add_error_kcal
import cherrytip.composeapp.generated.resources.recipe_add_error_name
import cherrytip.composeapp.generated.resources.recipe_add_error_protein
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.jetbrains.compose.resources.StringResource
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStore.State

interface AddRecipeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack: Intent

        data class ChangeImage(val image: ImageBitmap?): Intent
        data class ChangeName(val text: String): Intent
        data class ChangeDescr(val text: String): Intent
        data class ChangeCookingTime(val time: Int?): Intent
        data class ChangeProtein(val protein: Int?): Intent
        data class ChangeCarbs(val carbs: Int?): Intent
        data class ChangeFat(val fat: Int?): Intent
        data class ChangeKcal(val kcal: Int?): Intent
        data class ChangeDifficulty(val difficulty: Difficulty?): Intent
        data class ChangeMeals(val meals: TagEntity?): Intent
        data class ChangePreparation(val preparation: TagEntity?): Intent
        data class ChangeDiets(val diets: TagEntity?): Intent
    }

    data class State(
        val image: ImageBitmap?,
        val imageIsError: StringResource? = null,
        val title: String,
        val nameIsError: StringResource? = null,
        val descr: String,
        val cookingTime: Int?,
        val protein: Int?,
        val proteinIsError: StringResource? = null,
        val carbs: Int?,
        val carbsIsError: StringResource? = null,
        val fat: Int?,
        val fatIsError: StringResource? = null,
        val kcal: Int?,
        val kcalIsError: StringResource? = null,
        val difficulty: Difficulty?,
        val meals: TagEntity?,
        val preparation: TagEntity?,
        val diets: TagEntity?,
        val screenState: RecipeAddedState
    ){

        sealed interface RecipeAddedState{
            data object Initial: RecipeAddedState
            data class Error(val message: String): RecipeAddedState
            data object Loading: RecipeAddedState
        }

    }

    sealed interface Label {
        data object ClickBack: Label
        data object RecipeAdded: Label
    }
}

class AddRecipeStoreFactory(
    private val storeFactory: StoreFactory
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
                screenState = State.RecipeAddedState.Initial
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeImage(val image: ImageBitmap?): Msg
        data class ChangeImageError(val message: StringResource?): Msg

        data class ChangeName(val text: String): Msg
        data class ChangeNameError(val message: StringResource?): Msg

        data class ChangeDescr(val text: String): Msg

        data class ChangeCookingTime(val time: Int?): Msg

        data class ChangeProtein(val protein: Int?): Msg
        data class ChangeProteinError(val message: StringResource?): Msg

        data class ChangeCarbs(val carbs: Int?): Msg
        data class ChangeCarbsError(val message: StringResource?): Msg

        data class ChangeFat(val fat: Int?): Msg
        data class ChangeFatError(val message: StringResource?): Msg

        data class ChangeKcal(val kcal: Int?): Msg
        data class ChangeKcalError(val message: StringResource?): Msg

        data class ChangeDifficulty(val difficulty: Difficulty?): Msg

        data class ChangeMeals(val meals: TagEntity?): Msg

        data class ChangePreparation(val preparation: TagEntity?): Msg

        data class ChangeDiets(val diets: TagEntity?): Msg

        data object ScreenStateLoading: Msg
        data class ScreenStateError(val msg: String): Msg
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                is Intent.ChangeImage -> {
                    val image = intent.image
                    dispatch(Msg.ChangeImage(image))
                    image ?: dispatch(Msg.ChangeImageError(Res.string.recipe_add_error_image))
                }
                is Intent.ChangeName -> {
                    val text = intent.text.trim()
                    dispatch(Msg.ChangeName(text))
                    if (text.length !in (1..40)) dispatch(Msg.ChangeNameError(Res.string.recipe_add_error_name))
                }
                is Intent.ChangeDescr -> dispatch(Msg.ChangeDescr(intent.text))
                is Intent.ChangeCookingTime -> dispatch(Msg.ChangeCookingTime(intent.time))
                is Intent.ChangeProtein -> {
                    val protein = intent.protein
                    dispatch(Msg.ChangeProtein(protein))
                    protein ?: Msg.ChangeProteinError(Res.string.recipe_add_error_protein)
                }
                is Intent.ChangeCarbs -> {
                    val carbs = intent.carbs
                    dispatch(Msg.ChangeCarbs(carbs))
                    carbs ?: Msg.ChangeCarbsError(Res.string.recipe_add_error_carbs)
                }
                is Intent.ChangeFat -> {
                    val fat = intent.fat
                    dispatch(Msg.ChangeFat(fat))
                    fat ?: Msg.ChangeFatError(Res.string.recipe_add_error_fat)
                }
                is Intent.ChangeKcal -> {
                    val kcal = intent.kcal
                    dispatch(Msg.ChangeKcal(kcal))
                    kcal ?: Msg.ChangeKcalError(Res.string.recipe_add_error_kcal)
                }
                is Intent.ChangeDifficulty -> dispatch(Msg.ChangeDifficulty(intent.difficulty))
                is Intent.ChangeMeals -> dispatch(Msg.ChangeMeals(intent.meals))
                is Intent.ChangePreparation -> dispatch(Msg.ChangePreparation(intent.preparation))
                is Intent.ChangeDiets -> dispatch(Msg.ChangeDiets(intent.diets))
                Intent.ClickBack -> publish(Label.ClickBack)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChangeCarbs -> copy(carbs = message.carbs)
            is Msg.ChangeCarbsError -> copy(carbsIsError = message.message)
            is Msg.ChangeCookingTime -> copy(cookingTime = message.time)
            is Msg.ChangeDescr -> copy(descr = message.text)
            is Msg.ChangeDiets -> copy(diets = message.diets)
            is Msg.ChangeDifficulty -> copy(difficulty = message.difficulty)
            is Msg.ChangeFat -> copy(fat = message.fat)
            is Msg.ChangeFatError -> copy(fatIsError = message.message)
            is Msg.ChangeImage -> copy(image = message.image)
            is Msg.ChangeImageError -> copy(imageIsError = message.message)
            is Msg.ChangeKcal -> copy(kcal = message.kcal)
            is Msg.ChangeKcalError -> copy(kcalIsError = message.message)
            is Msg.ChangeMeals -> copy(meals = message.meals)
            is Msg.ChangeName -> copy(title = message.text)
            is Msg.ChangeNameError -> copy(nameIsError = message.message)
            is Msg.ChangePreparation -> copy(preparation = message.preparation)
            is Msg.ChangeProtein -> copy(protein = message.protein)
            is Msg.ChangeProteinError -> copy(proteinIsError = message.message)
            is Msg.ScreenStateError -> copy(screenState = State.RecipeAddedState.Error(message.msg))
            Msg.ScreenStateLoading -> copy(screenState = State.RecipeAddedState.Loading)
        }
    }
}
