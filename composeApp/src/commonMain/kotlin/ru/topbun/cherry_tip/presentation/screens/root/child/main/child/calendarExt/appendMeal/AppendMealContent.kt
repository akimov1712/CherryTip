package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.add
import cherrytip.composeapp.generated.resources.add_recipe
import cherrytip.composeapp.generated.resources.added
import cherrytip.composeapp.generated.resources.breakfast
import cherrytip.composeapp.generated.resources.dinner
import cherrytip.composeapp.generated.resources.empty
import cherrytip.composeapp.generated.resources.filter
import cherrytip.composeapp.generated.resources.ic_add
import cherrytip.composeapp.generated.resources.ic_append
import cherrytip.composeapp.generated.resources.ic_cancel
import cherrytip.composeapp.generated.resources.ic_filter
import cherrytip.composeapp.generated.resources.lunch
import cherrytip.composeapp.generated.resources.snack
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.choiceTag.ChoiceTagModal
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.detailRecipe.DetailRecipeModal
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeTabs
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.NotFoundContent
import ru.topbun.cherry_tip.presentation.ui.components.RecipeItem
import ru.topbun.cherry_tip.presentation.ui.components.RecipeShortWithButtonItem
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun AppendMealScreen(
    component: AppendMealComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    val state by component.state.collectAsState()
    val scope =  rememberCoroutineScope()
    val snackbar = SnackbarHostState()
    var isOpenModalChoiceTags by rememberSaveable { mutableStateOf(false) }
    var openDetailRecipeModal by remember { mutableStateOf<RecipeEntity?>(null) }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val titleRes = when(state.calendarType){
                    CalendarType.Breakfast -> Res.string.breakfast
                    CalendarType.Lunch -> Res.string.lunch
                    CalendarType.Dinner -> Res.string.dinner
                    CalendarType.Snack -> Res.string.snack
                }
                BackWithTitle(stringResource(Res.string.add) + " " + stringResource(titleRes)) { component.clickBack() }
                TextFields.Search(
                    value = state.query,
                    onValueChange = { if (it.length <= 40) component.changeQuery(it) },
                )
                CustomTabRow(
                    selectedIndex = state.selectedIndex,
                    items = state.tabs.map { stringResource(it.titleRes) }
                ) {
                    component.changeTab(it)
                }
                val messageAdd = stringResource(Res.string.added)
                Recipes(
                    component = component,
                    modifier = Modifier.fillMaxWidth(),
                    onClickAppend = {
                      scope.launch{ snackbar.showSnackbar("$messageAdd $it" ) }
                    }
                ){
                    openDetailRecipeModal = it
                }
            }
            RecipeButtons(component){
                isOpenModalChoiceTags = true
            }
        }
        ModalChoiceTag(isOpenModalChoiceTags, component){ isOpenModalChoiceTags = false }
        openDetailRecipeModal?.let {
            DetailRecipeModal(
                recipe = it,
                isMyRecipe = it.userId == state.userId,
                onClickMyRecipe = {
                    component.deleteRecipe(it.id)
                    openDetailRecipeModal = null
                }
            ){ openDetailRecipeModal = null }
        }
    }
}

@Composable
private fun Recipes(
    component: AppendMealComponent,
    modifier: Modifier = Modifier,
    onClickAppend: (String) -> Unit,
    onClickRecipe: (RecipeEntity) -> Unit
) {
    val state by component.state.collectAsState()
    val screenState = state.recipeState
    if (state.recipes.isEmpty()) {
        Box(
            modifier,
            contentAlignment = Alignment.Center
        ) {
            if (screenState == AppendMealStore.State.RecipeState.Loading) {
                CircularProgressIndicator(color = Colors.Purple)
            }
            if (screenState == AppendMealStore.State.RecipeState.Result) {
                NotFoundContent(Modifier.padding(horizontal = 20.dp).align(Alignment.Center))
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = state.recipes, key = { it.id }) { recipe ->
            RecipeShortWithButtonItem(
                recipe = recipe,
                icon = painterResource(Res.drawable.ic_append),
                onClickItem = onClickRecipe
            ) {
                onClickAppend(recipe.title)
                component.appendMeal(recipe.id)
            }
        }
        item {
            if (!state.isEndList) {
                if (screenState == AppendMealStore.State.RecipeState.Result || screenState == AppendMealStore.State.RecipeState.Initial) {
                    LaunchedEffect(state.recipes.toString()) {
                        component.loadRecipes()
                    }
                } else if (screenState == AppendMealStore.State.RecipeState.Loading && state.recipes.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
            }
        }
    }
}

@Composable
private fun ModalChoiceTag(
    isOpenModalChoiceTags: Boolean,
    component: AppendMealComponent,
    onDismiss: () -> Unit
) {
    val state by component.state.collectAsState()
    if (isOpenModalChoiceTags) {
        var categories = CategoriesEntity(emptyList(), emptyList(), emptyList())
        var isLoadingTags = true
        when (val choiceTagState = state.choiceTagState) {
            AppendMealStore.State.ChoiceTagState.Error -> isLoadingTags = false
            AppendMealStore.State.ChoiceTagState.Loading -> isLoadingTags = true
            is AppendMealStore.State.ChoiceTagState.Result -> {
                isLoadingTags = false
                categories = choiceTagState.categories
            }

            else -> {}
        }

        ChoiceTagModal(
            categories = categories,
            choiceMealId = state.meal,
            choicePreparationId = state.preparations,
            choiceDietsId = state.diets,
            onDismiss = { onDismiss() },
            isLoading = isLoadingTags,
            onClickRetry = { component.loadCategory() },
            onSave = { meal, preparation, diets ->
                onDismiss()
                component.changeTags(meal?.id, preparation?.id, diets?.id)
            }
        )
    }
}

@Composable
private fun BoxScope.RecipeButtons(
    component: AppendMealComponent,
    onClickFilter: () -> Unit
) {
    val state by component.state.collectAsState()
    val isMyRecipe = state.tabs[state.selectedIndex] == RecipeTabs.Recipes
    val text = stringResource(if (!isMyRecipe) Res.string.add_recipe else Res.string.filter)
    val icon = painterResource(if (!isMyRecipe) Res.drawable.ic_add else Res.drawable.ic_filter)
    Button(
        text = text,
        icon = icon
    ) {
        if (isMyRecipe) onClickFilter()
        else component.clickAddRecipe()
    }

}

@Composable
private fun BoxScope.Button(text: String, icon: Painter, onClick: () -> Unit) {
    Buttons.Purple(
        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp).height(48.dp),
        onClick = onClick,
        contentPadding = PaddingValues(24.dp, 12.dp),
    ) {
        Icon(
            painter = icon,
            contentDescription = text
        )
        Spacer(Modifier.width(4.5.dp))
        Texts.Button(
            text = text,
            fontSize = 16.sp
        )
    }
}