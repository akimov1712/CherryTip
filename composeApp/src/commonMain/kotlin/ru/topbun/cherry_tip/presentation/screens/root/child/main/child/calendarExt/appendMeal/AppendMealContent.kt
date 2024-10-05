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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.add_meal
import cherrytip.composeapp.generated.resources.add_recipe
import cherrytip.composeapp.generated.resources.empty
import cherrytip.composeapp.generated.resources.filter
import cherrytip.composeapp.generated.resources.ic_add
import cherrytip.composeapp.generated.resources.ic_filter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.State.RecipeState
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeTabs
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.RecipeItem
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun AppendMealScreen(
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
    ) {
        BackWithTitle(stringResource(Res.string.add_meal)) { }
        Spacer(Modifier.height(30.dp))
        TextFields.Search(
            value = "",
            onValueChange = { if (it.length <= 40) TODO() },
        )
        Spacer(Modifier.height(16.dp))
        CustomTabRow(
            selectedIndex = TODO(),
            items = TODO()
        ) {

        }
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth().weight(1f)){
            Recipes(
                component = TODO(),
                modifier = Modifier.fillMaxWidth()
            ) {

            }
            RecipeButtons(
                component = TODO()
            ){

            }
        }
    }


}

@Composable
private fun Recipes(
    component: RecipeComponent,
    modifier: Modifier = Modifier,
    onClickRecipe: (RecipeEntity) -> Unit
) {
    val state by component.state.collectAsState()
    val screenState = state.recipeState
    if (state.recipes.isEmpty()) {
        Box(
            modifier,
            contentAlignment = Alignment.Center
        ) {
            if (screenState == RecipeState.Loading) {
                CircularProgressIndicator(color = Colors.Purple)
            }
            if (screenState == RecipeState.Result) {
                Texts.Option(stringResource(Res.string.empty), color = Colors.Black)
            }
        }
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = state.recipes, key = { it.id }) {
            RecipeItem(recipe = it, onClickRecipe = onClickRecipe)
        }
        item {
            if (!state.isEndList) {
                if (screenState == RecipeState.Result || screenState == RecipeState.Initial) {
                    LaunchedEffect(state.recipes.toString()) {
                        component.loadRecipes()
                    }
                } else if (screenState == RecipeState.Loading && state.recipes.isNotEmpty()) {
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
private fun BoxScope.RecipeButtons(
    component: RecipeComponent,
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