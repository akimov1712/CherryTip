package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.detailRecipe

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.delete_recipe
import cherrytip.composeapp.generated.resources.ic_chef
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_delete
import cherrytip.composeapp.generated.resources.ic_fire
import cherrytip.composeapp.generated.resources.unknown
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.AppModalBottomSheet
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Nutrients
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.formatMinutesToTime
import ru.topbun.cherry_tip.utills.toStringOrBlank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRecipeModal(
    recipe: RecipeEntity,
    isMyRecipe: Boolean = true,
    onClickMyRecipe: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Hidden
    )
    AppModalBottomSheet(
        state = sheetState,
        onDismiss = { scope.launch { sheetState.hide(); onDismiss() } }
    ){
        ModalContent(recipe,isMyRecipe,onClickMyRecipe)
    }
}

@Composable
private fun ModalContent(
    recipe: RecipeEntity,
    isMyRecipe: Boolean,
    onClickMyRecipe: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Colors.White)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RecipeImage(recipe)
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.Title(text = recipe.title)
                RecipeInformation(recipe)
                Nutrients(recipe)
                Description(recipe)
            }
            if(isMyRecipe){
                Spacer(Modifier.height(20.dp))
                Buttons.Button(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = onClickMyRecipe,
                    containerColor = Colors.Red
                ){
                    Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete),
                            contentDescription = null,
                            tint = Colors.White
                        )
                        Texts.Button(
                            text = stringResource(Res.string.delete_recipe),
                            color = Colors.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Description(recipe: RecipeEntity) {
    Box(Modifier.fillMaxWidth().heightIn(min = 80.dp)) {
        Texts.General(recipe.descr.toStringOrBlank())
    }
}


@Composable
private fun RecipeInformation(recipe: RecipeEntity) {
    Row(modifier = Modifier.fillMaxWidth()) {
        IconWithText(
            icon = Res.drawable.ic_fire,
            text = "${recipe.calories} kcal"
        )
        IconWithText(
            icon = Res.drawable.ic_clock,
            text = recipe.cookingTime?.formatMinutesToTime() ?: stringResource(Res.string.unknown)
        )
        IconWithText(
            icon = Res.drawable.ic_chef,
            text = recipe.difficulty?.toString() ?: stringResource(Res.string.unknown)
        )
    }
}

@Composable
private fun RowScope.IconWithText(icon: DrawableResource, text: String) {
    Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(19.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = Colors.DarkGray
        )
        Spacer(Modifier.width(7.dp))
        Texts.Option(
            text = text,
            fontSize = 16.sp,
            color = Colors.DarkGray
        )
    }
}

@Composable
private fun RecipeImage(recipe: RecipeEntity) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        var isLoading by rememberSaveable { mutableStateOf(true) }
        if (isLoading) CircularProgressIndicator(
            color = Colors.Purple,
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp
        )
        AsyncImage(
            modifier = Modifier.fillMaxWidth().background(Colors.Blue),
            model = recipe.image,
            contentDescription = recipe.title,
            onState = { isLoading = it is AsyncImagePainter.State.Loading },
            contentScale = ContentScale.FillWidth
        )
    }
}
