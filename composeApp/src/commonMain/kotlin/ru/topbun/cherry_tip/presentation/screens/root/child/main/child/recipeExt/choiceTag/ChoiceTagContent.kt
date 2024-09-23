package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.choiceTag

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.clear
import cherrytip.composeapp.generated.resources.diets
import cherrytip.composeapp.generated.resources.meals
import cherrytip.composeapp.generated.resources.preparation_method
import cherrytip.composeapp.generated.resources.retry
import cherrytip.composeapp.generated.resources.save
import cherrytip.composeapp.generated.resources.tag_loading_error
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.decode.Decoder
import coil3.request.ImageRequest
import coil3.size.Size
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.AppModalBottomSheet
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceTagModal(
    categories: CategoriesEntity,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClickRetry: () -> Unit = {},
    onSave: (meal: TagEntity?, preparation: TagEntity?, diets: TagEntity?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Hidden
    )
    AppModalBottomSheet(
        modifier = modifier,
        state = sheetState,
        onDismiss = { scope.launch { sheetState.hide(); onDismiss() } }
    ){
        ModalContent(categories, isLoading, onClickRetry, onSave)
    }
}

@Composable
private fun ModalContent(
    categories: CategoriesEntity,
    isLoading: Boolean,
    onClickRetry: () -> Unit,
    onSave: (meal: TagEntity?, preparation: TagEntity?, diets: TagEntity?) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Colors.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        var mealsSelectedId by rememberSaveable { mutableStateOf<Int?>(null) }
        var preparationSelectedId by rememberSaveable { mutableStateOf<Int?>(null) }
        var dietsSelectedId by rememberSaveable { mutableStateOf<Int?>(null) }
        CategoriesItem(
            title = stringResource(Res.string.meals),
            tags = categories.types,
            selectedId = mealsSelectedId,
            isLoading = isLoading,
            onClickRetry = onClickRetry,
            onClickItem = { mealsSelectedId = it }
        )
        CategoriesItem(
            title = stringResource(Res.string.preparation_method),
            tags = categories.preparations,
            selectedId = preparationSelectedId,
            isLoading = isLoading,
            onClickRetry = onClickRetry,
            onClickItem = { preparationSelectedId = it }
        )
        CategoriesItem(
            title = stringResource(Res.string.diets),
            tags = categories.diets,
            selectedId = dietsSelectedId,
            isLoading = isLoading,
            onClickRetry = onClickRetry,
            onClickItem = { dietsSelectedId = it }
        )
        Buttons(
            onClickClear = {
                mealsSelectedId = null
                preparationSelectedId = null
                dietsSelectedId = null
            },
            onClickSave = {
                onSave(
                    mealsSelectedId?.let { selectId -> categories.types.first { it.id == selectId } },
                    preparationSelectedId?.let { selectId -> categories.preparations.first { it.id == selectId } },
                    dietsSelectedId?.let { selectId -> categories.diets.first { it.id == selectId } }
                )
            }
        )
    }
}

@Composable
private fun Buttons(
    onClickClear: () -> Unit,
    onClickSave: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Buttons.Gray(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            onClick = { onClickClear() }
        ) {
            Texts.Button(
                text = stringResource(Res.string.clear),
                color = Colors.Gray
            )
        }
        Buttons.Purple(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            onClick = { onClickSave() }
        ) {
            Texts.Button(
                text = stringResource(Res.string.save),
                color = Colors.White
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoriesItem(title: String, tags: List<TagEntity>, selectedId: Int?, isLoading: Boolean, onClickRetry: () -> Unit, onClickItem: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Texts.Option(
            text = title,
            color = Colors.Black
        )
        Spacer(Modifier.height(10.dp))
        when{
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 24.dp), color = Colors.Purple)
            !isLoading && tags.isEmpty() -> {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Texts.General(stringResource(Res.string.tag_loading_error))
                    Spacer(Modifier.height(12.dp))
                    Buttons.Purple(
                        onClick = onClickRetry
                    ){
                        Texts.Button(stringResource(Res.string.retry))
                    }
                }
            }
            !isLoading && tags.isNotEmpty() -> {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    tags.forEach {
                        TagItem(
                            tag = it,
                            isSelected = it.id == selectedId
                        ){
                            onClickItem(it)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}

@Composable
private fun TagItem(tag: TagEntity, isSelected: Boolean, onClick: (Int) -> Unit) {
    val background = if(isSelected) Colors.Purple else Colors.PurpleBackground
    val textColor = if(isSelected) Colors.White else Colors.Gray
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable {
                onClick(tag.id)
            }
            .padding(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(24.dp),
            model = tag.icon,
            contentDescription = null
        )
        Spacer(Modifier.width(7.dp))
        Texts.Option(
            text = tag.title,
            color = textColor,
            fontSize = 16.sp
        )
    }
}