package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources._0_g
import cherrytip.composeapp.generated.resources._0_min
import cherrytip.composeapp.generated.resources.add
import cherrytip.composeapp.generated.resources.add_recipe
import cherrytip.composeapp.generated.resources.carbs_100
import cherrytip.composeapp.generated.resources.details
import cherrytip.composeapp.generated.resources.diets
import cherrytip.composeapp.generated.resources.difficulty
import cherrytip.composeapp.generated.resources.fat_100
import cherrytip.composeapp.generated.resources.ic_send_image
import cherrytip.composeapp.generated.resources.kcal_100
import cherrytip.composeapp.generated.resources.meals
import cherrytip.composeapp.generated.resources.numerical_indicators
import cherrytip.composeapp.generated.resources.preparation_method
import cherrytip.composeapp.generated.resources.preparation_time
import cherrytip.composeapp.generated.resources.protein_100
import cherrytip.composeapp.generated.resources.recipe_name
import cherrytip.composeapp.generated.resources.short_descr
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.Difficulty
import ru.topbun.cherry_tip.domain.entity.recipe.CategoriesEntity
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components.NumericalDropMenuContent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components.NumericalTagItem
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components.NumericalTextField
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.choiceTag.ChoiceTagModal
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.isNumber
import ru.topbun.cherry_tip.utills.toStringOrBlank
import kotlin.random.Random

@Composable
fun AddRecipeScreen(
    component: AddRecipeComponent,
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
        BackWithTitle(stringResource(Res.string.add_recipe)) { component.clickBack() }
        Spacer(Modifier.height(30.dp))
        Details(component)
        Box(modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp).height(1.dp))
        NumericalContent(component){ isOpenModalChoiceTags = true }
        Buttons.Purple(
            modifier = Modifier.fillMaxWidth().height(57.dp),
            onClick = {}
        ){
            Texts.Button(text = stringResource(Res.string.add))
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
        ChoiceTagModal(
            categories = categories,
            onDismiss = { isOpenModalChoiceTags = false },
            onSave = { meal, preparation, diets ->
                component.changeMeals(meal)
                component.changePreparation(preparation)
                component.changeDiets(diets)
            }
        )
    }

}

@Composable
private fun NumericalContent(component: AddRecipeComponent, onOpenModal: () -> Unit) {
    val state by component.getState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Texts.Option(
            text = stringResource(Res.string.numerical_indicators),
            color = Colors.Black
        )
        NumericalTextField(
            title = stringResource(Res.string.preparation_time),
            placeholderText = stringResource(Res.string._0_min),
            value = state.cookingTime.toStringOrBlank(),
            onValueChange = {
                if ((it.isEmpty() || it.isNumber()) && it.length < 5) {
                    component.changeCookingTime(it.toIntOrNull())
                }
            }
        )
        NumericalTextField(
            title = stringResource(Res.string.protein_100),
            placeholderText = stringResource(Res.string._0_g),
            value = state.protein.toStringOrBlank(),
            isImportant = true,
            onValueChange = { component.changeProtein(it.toIntOrNull()) }
        )

        NumericalTextField(
            title = stringResource(Res.string.carbs_100),
            placeholderText = stringResource(Res.string._0_g),
            value = state.carbs.toStringOrBlank(),
            isImportant = true,
            onValueChange = { }
        )

        NumericalTextField(
            title = stringResource(Res.string.fat_100),
            placeholderText = stringResource(Res.string._0_g),
            value = state.fat.toStringOrBlank(),
            isImportant = true,
            onValueChange = { }
        )

        NumericalTextField(
            title = stringResource(Res.string.kcal_100),
            placeholderText = stringResource(Res.string._0_g),
            isImportant = true,
            value = state.kcal.toStringOrBlank(),
            onValueChange = { }
        )
        NumericalDropMenuContent(
            title = stringResource(Res.string.difficulty),
            items = listOf<Difficulty?>(null) + Difficulty.values(),
            selectedItem = state.difficulty,
            onValueChange = { component.changeDifficulty(it)}
        )
        NumericalTagItem(
            title = stringResource(Res.string.meals),
            tag = state.meals,
            onClickChangeTag = { onOpenModal() }
        )
        NumericalTagItem(
            title = stringResource(Res.string.preparation_method),
            tag = state.preparation,
            onClickChangeTag = { onOpenModal() }
        )
        NumericalTagItem(
            title = stringResource(Res.string.diets),
            tag = state.diets,
            onClickChangeTag = { onOpenModal() }
        )
    }
}

@Composable
private fun Details(component: AddRecipeComponent) {
    val state by component.getState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Texts.Option(
            text = stringResource(Res.string.details),
            color = Colors.Black
        )
        ButtonChoiceImage(component)
        TextFields.OutlinedTextField(
            value = state.title,
            onValueChange = { if (it.length <= 40) component.changeTitle(it)},
            placeholderText = stringResource(Res.string.recipe_name)
        )
        TextFields.OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 100.dp),
            value = state.descr,
            onValueChange = { if (it.length <= 500) component.changeDescr(it) },
            placeholderText = stringResource(Res.string.short_descr),
            singleLine = false
        )
    }
}

@Composable
private fun ButtonChoiceImage(component: AddRecipeComponent) {
    val state by component.getState()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = rememberCoroutineScope(),
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                component.changeImage(it.toImageBitmap())
            }
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White)
            .border(2.dp, Colors.PurpleBackground, RoundedCornerShape(16.dp))
            .clickable {
                singleImagePicker.launch()
            },
        contentAlignment = Alignment.Center
    ) {
        state.image?.let {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Colors.PurpleBackground.copy(0.2f))
                    .clickable { component.changeImage(null)}
                    .padding(8.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Colors.White
            )
        } ?: Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_send_image),
            contentDescription = null,
            tint = Colors.Purple
        )
    }
}

@Composable
private fun AddRecipeComponent.getState() = this.state.collectAsState()