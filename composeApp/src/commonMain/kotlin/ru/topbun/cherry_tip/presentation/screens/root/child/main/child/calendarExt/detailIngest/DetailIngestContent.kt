package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.add_meal
import cherrytip.composeapp.generated.resources.breakfast
import cherrytip.composeapp.generated.resources.dinner
import cherrytip.composeapp.generated.resources.empty
import cherrytip.composeapp.generated.resources.ic_add
import cherrytip.composeapp.generated.resources.ic_cancel
import cherrytip.composeapp.generated.resources.lunch
import cherrytip.composeapp.generated.resources.snack
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.detailRecipe.DetailRecipeModal
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.InfoRecipeAction
import ru.topbun.cherry_tip.presentation.ui.components.NotFoundContent
import ru.topbun.cherry_tip.presentation.ui.components.RecipeItem
import ru.topbun.cherry_tip.presentation.ui.components.RecipeShortWithButtonItem
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import kotlin.math.roundToInt

@Composable
fun DetailIngestionScreen(
    component: DetailIngestComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    val state by component.state.collectAsState()
    var openDetailRecipeModal by remember { mutableStateOf<RecipeEntity?>(null) }
    LaunchedEffect(Unit) { component.load() }
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        val titleRes = when(state.calendarType){
            CalendarType.Breakfast -> Res.string.breakfast
            CalendarType.Lunch -> Res.string.lunch
            CalendarType.Dinner -> Res.string.dinner
            CalendarType.Snack -> Res.string.snack
        }
        BackWithTitle(stringResource(titleRes)) { component.clickBack() }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                when(val screenState = state.calendarState){
                    is DetailIngestStore.State.CalendarTypeState.Error -> {
                        ErrorContent(modifier = Modifier.padding(horizontal = 20.dp), text = screenState.msg){
                            component.load()
                        }
                    }
                    DetailIngestStore.State.CalendarTypeState.Loading -> CircularProgressIndicator(color = Colors.Purple)
                    DetailIngestStore.State.CalendarTypeState.Result -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Information(component)
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                RecipeList(component){ openDetailRecipeModal = it }
                                ButtonAddMeal{ component.clickAddMeal() }
                            }
                        }
                    }
                    else -> {}
                }

            }
        }

    openDetailRecipeModal?.let {
        DetailRecipeModal(it){ openDetailRecipeModal = null }
    }
}

@Composable
private fun BoxScope.ButtonAddMeal(onClick: () -> Unit) {
    Buttons.Purple(
        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp).height(48.dp),
        onClick = onClick,
        contentPadding = PaddingValues(24.dp, 12.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_add),
            contentDescription = stringResource(Res.string.add_meal)
        )
        Spacer(Modifier.width(4.5.dp))
        Texts.Button(
            text = stringResource(Res.string.add_meal),
            fontSize = 16.sp
        )
    }
}

@Composable
private fun RecipeList(component: DetailIngestComponent, onClickRecipe: (RecipeEntity) -> Unit) {
    val state by component.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        when(val screenState = state.recipesState){
            is DetailIngestStore.State.RecipesState.Error -> {
                ErrorContent(modifier = Modifier.padding(horizontal = 20.dp), text = screenState.msg){
                    component.load()
                }
            }
            DetailIngestStore.State.RecipesState.Loading -> CircularProgressIndicator(color = Colors.Purple)
            else -> {}
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.recipeList.isEmpty()) item { NotFoundContent(modifier = Modifier.align(Alignment.Center).padding(horizontal = 20.dp)) }
            items(items = state.recipeList, key = null) {
                RecipeShortWithButtonItem(
                    recipe = it,
                    icon = painterResource(Res.drawable.ic_cancel),
                    onClickItem = onClickRecipe
                ) { component.cancelRecipe(it) }
            }
        }
    }

}

@Composable
private fun Information(component: DetailIngestComponent) {
    val state by component.state.collectAsState()
    val calendar = state.getCalendar()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val eatenCalories = calendar.recipes.sumOf { it.calories }
            Texts.Title(
                text = "$eatenCalories/${state.needCalories}",
                fontSize = 16.sp
            )
            Texts.Light(
                text = "Calories",
                fontSize = 14.sp
            )
        }
        Row {
            val protein = calendar.recipes.sumOf { it.protein.toFloat().roundToInt() }
            val carbs = calendar.recipes.sumOf { it.carbs.toFloat().roundToInt() }
            val fat = calendar.recipes.sumOf { it.fat.toFloat().roundToInt() }
            ValueWithProperty(
                value = protein.toString(),
                property = "Protein"
            )
            ValueWithProperty(
                value = carbs.toString(),
                property = "Carbs"
            )
            ValueWithProperty(
                value = fat.toString(),
                property = "Fat"
            )
        }
    }
}

@Composable
private fun ValueWithProperty(
    value: String,
    property: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Texts.Option(
            text = value,
            fontSize = 16.sp,
            color = Colors.Black
        )
        Texts.Light(text = property)
    }
}

private fun DetailIngestStore.State.getCalendar() = this.calendarRecipes ?: throw RuntimeException("calendar is not found")