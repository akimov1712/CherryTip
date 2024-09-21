package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources._0_g
import cherrytip.composeapp.generated.resources._0_min
import cherrytip.composeapp.generated.resources.add_recipe
import cherrytip.composeapp.generated.resources.carbs_100
import cherrytip.composeapp.generated.resources.details
import cherrytip.composeapp.generated.resources.diets
import cherrytip.composeapp.generated.resources.difficulty
import cherrytip.composeapp.generated.resources.fat_100
import cherrytip.composeapp.generated.resources.ic_important
import cherrytip.composeapp.generated.resources.ic_send_image
import cherrytip.composeapp.generated.resources.kcal_100
import cherrytip.composeapp.generated.resources.meals
import cherrytip.composeapp.generated.resources.not_selected
import cherrytip.composeapp.generated.resources.numerical_indicators
import cherrytip.composeapp.generated.resources.preparation_method
import cherrytip.composeapp.generated.resources.preparation_time
import cherrytip.composeapp.generated.resources.protein_100
import cherrytip.composeapp.generated.resources.recipe_name
import cherrytip.composeapp.generated.resources.short_descr
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.choiceTag.ChoiceTagModal
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.TextFields.textStyle
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.presentation.ui.utills.localWidth
import ru.topbun.cherry_tip.utills.isNumber
import kotlin.random.Random

@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val scrollState = rememberScrollState()
    var isOpenModalChoiceTags by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        BackWithTitle(stringResource(Res.string.add_recipe)) { }
        Spacer(Modifier.height(30.dp))
        Details()
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp).height(1.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Texts.Option(
                text = stringResource(Res.string.numerical_indicators),
                color = Colors.Black
            )
            var value by rememberSaveable {
                mutableStateOf("")
            }
            NumericalTextField(
                title = stringResource(Res.string.preparation_time),
                placeholderText = stringResource(Res.string._0_min),
                value = value,
                onValueChange = {
                    if ((it.isEmpty() || it.isNumber()) && it.length < 5) {
                        value = it
                    }
                }
            )
            NumericalTextField(
                title = stringResource(Res.string.protein_100),
                placeholderText = stringResource(Res.string._0_g),
                value = value,
                isImportant = true,
                onValueChange = { }
            )

            NumericalTextField(
                title = stringResource(Res.string.carbs_100),
                placeholderText = stringResource(Res.string._0_g),
                value = value,
                isImportant = true,
                onValueChange = { }
            )

            NumericalTextField(
                title = stringResource(Res.string.fat_100),
                placeholderText = stringResource(Res.string._0_g),
                value = value,
                isImportant = true,
                onValueChange = { }
            )

            NumericalTextField(
                title = stringResource(Res.string.kcal_100),
                placeholderText = stringResource(Res.string._0_g),
                isImportant = true,
                value = value,
                onValueChange = { }
            )
            var selectedDiff by remember {
                mutableStateOf<Difficulty?>(null)
            }
            NumericalDropMenuContent(
                title = stringResource(Res.string.difficulty),
                items = listOf<Difficulty?>(null) + Difficulty.values(),
                selectedItem = selectedDiff,
                onValueChange = { selectedDiff = it }
            )
            NumericalTagItem(
                title = stringResource(Res.string.meals),
                tag = null,
                onClickChangeTag = { isOpenModalChoiceTags = true }
            )
            NumericalTagItem(
                title = stringResource(Res.string.preparation_method),
                tag = null,
                onClickChangeTag = { isOpenModalChoiceTags = true }
            )
            NumericalTagItem(
                title = stringResource(Res.string.diets),
                tag = null,
                onClickChangeTag = { isOpenModalChoiceTags = true }
            )
        }
    }
    if (isOpenModalChoiceTags) {
        val tags = mutableListOf<TagEntity>().apply {
            repeat(10) {
                add(
                    TagEntity(
                        it,
                        Random.nextInt(0, 1000000).toString(),
                        "https://thumbs.dreamstime.com/b/иллюстрация-жареного-куриного-яйца-на-тарелке-изображения-для-240298062.jpg"
                    )
                )
            }
        }
        val categories = CategoriesEntity(
            types = tags,
            preparations = tags,
            diets = tags
        )
        ChoiceTagModal(categories) { isOpenModalChoiceTags = false }
    }

}

@Composable
private fun NumericalTagItem(
    title: String,
    tag: TagEntity?,
    isImportant: Boolean = false,
    onClickChangeTag: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Texts.Option(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.Gray,
                    fontSize = 16.sp
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier.size(6.dp).align(Alignment.Top),
                        painter = painterResource(Res.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.Red
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .clickable { onClickChangeTag() }
                    .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(13.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                tag?.let {
                    AsyncImage(
                        modifier = Modifier.size(12.dp),
                        model = it.icon,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Texts.Button(
                        text = title,
                        color = Colors.Black
                    )
                } ?: Texts.Button(
                    text = stringResource(Res.string.not_selected),
                    color = Colors.Gray
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> NumericalDropMenuContent(
    title: String,
    items: List<T>,
    selectedItem: T,
    isImportant: Boolean = false,
    onValueChange: (T) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Texts.Option(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.Gray,
                    fontSize = 16.sp
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier.size(6.dp).align(Alignment.Top),
                        painter = painterResource(Res.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.Red
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            var isDropMenuVisible by rememberSaveable { mutableStateOf(false) }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .weight(1f),
                expanded = isDropMenuVisible,
                onExpandedChange = { isDropMenuVisible = !isDropMenuVisible }
            ) {
                TextFields.OutlinedDropDownMenu(
                    modifier = Modifier
                        .menuAnchor()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = selectedItem?.toString() ?: "",
                    placeholderText = stringResource(Res.string.not_selected),
                    isOpen = isDropMenuVisible
                )
                DropMenuDifficulty(
                    modifier = Modifier.localWidth(textFieldSize.width),
                    isDropMenuVisible = isDropMenuVisible,
                    items = items,
                    onValueChange = onValueChange,
                    onDismissRequest = { isDropMenuVisible = false }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}

@Composable
private fun <T> DropMenuDifficulty(
    modifier: Modifier = Modifier,
    isDropMenuVisible: Boolean,
    items: List<T>,
    onValueChange: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier.background(Colors.White),
        expanded = isDropMenuVisible,
        onDismissRequest = { onDismissRequest() },
    ) {
        items.forEach {
            DropdownMenuItem(
                text = {
                    Texts.General(
                        text = it?.toString()
                            ?: stringResource(Res.string.not_selected)
                    )
                },
                onClick = { onValueChange(it); onDismissRequest() }
            )
        }
    }
}

@Composable
private fun NumericalTextField(
    title: String,
    placeholderText: String,
    value: String,
    isImportant: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                Texts.Option(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.Gray,
                    fontSize = 16.sp
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier.size(6.dp).align(Alignment.Top),
                        painter = painterResource(Res.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.Red
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            TextFields.OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                placeholderText = placeholderText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
            )
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}

@Composable
private fun Details() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Texts.Option(
            text = stringResource(Res.string.details),
            color = Colors.Black
        )
        ButtonChoiceImage()
        var title by rememberSaveable {
            mutableStateOf("")
        }
        TextFields.OutlinedTextField(
            value = title,
            onValueChange = { if (it.length <= 40) title = it },
            placeholderText = stringResource(Res.string.recipe_name)
        )
        var descr by rememberSaveable {
            mutableStateOf("")
        }
        TextFields.OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 100.dp),
            value = descr,
            onValueChange = { if (it.length <= 500) descr = it },
            placeholderText = stringResource(Res.string.short_descr),
            singleLine = false
        )
    }
}

@Composable
private fun ButtonChoiceImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White)
            .border(2.dp, Colors.PurpleBackground, RoundedCornerShape(16.dp))
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_send_image),
            contentDescription = null,
            tint = Colors.Purple
        )
    }
}