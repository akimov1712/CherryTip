package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.empty
import cherrytip.composeapp.generated.resources.filter
import cherrytip.composeapp.generated.resources.ic_filter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStore.State.RecipeState
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun RecipeScreen(
    component: RecipeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val snackbar = SnackbarHostState()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ){
            Spacer(Modifier.height(20.dp))
            Column{
                val state by component.state.collectAsState()
                TextFields.Search(
                    value = state.query,
                    onValueChange = { if(it.length <= 40) component.changeQuery(it) },
                )
                Spacer(Modifier.height(16.dp))
                CustomTabRow(
                    selectedIndex = state.selectedIndex,
                    items = state.tabs.map { stringResource(it.titleRes) }
                ){
                    component.changeTab(it)
                }
                Spacer(Modifier.height(16.dp))
                Recipes(
                    component = component,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }
            Buttons.Purple(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
                onClick = { component.clickAddRecipe() },
                contentPadding = PaddingValues(24.dp, 12.dp),
            ){
                Icon(
                    painter = painterResource(Res.drawable.ic_filter),
                    contentDescription = "Filter"
                )
                Spacer(Modifier.width(4.5.dp))
                Texts.Button(
                    text = stringResource(Res.string.filter),
                    fontSize = 16.sp
                )
            }
        }

    }
}

@Composable
private fun Recipes(
     component: RecipeComponent,
     modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()
    val screenState = state.recipeState
    if (state.recipes.isEmpty()){
        Box(
            modifier,
            contentAlignment = Alignment.Center
        ){
            if (screenState == RecipeState.Loading) {
                CircularProgressIndicator(color = Colors.Purple)
            }
            if (screenState == RecipeState.Result){
                Texts.Option(stringResource(Res.string.empty), color = Colors.Black)
            }
        }
    }

    LazyColumn(
        modifier = modifier,
    ){
        items(items = state.recipes, key = { it.id }) {
            RecipeItem(it)
        }
        item {
            if (!state.isEndList){
                if (screenState == RecipeState.Result || screenState == RecipeState.Initial) {
                    LaunchedEffect(state.recipes.toString()) {
                        component.loadRecipes()
                    }
                } else if (screenState == RecipeState.Loading && state.recipes.isNotEmpty()){
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