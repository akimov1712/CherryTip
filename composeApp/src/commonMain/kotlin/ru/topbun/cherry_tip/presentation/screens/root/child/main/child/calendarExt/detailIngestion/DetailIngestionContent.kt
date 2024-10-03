package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngestion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.add_meal
import cherrytip.composeapp.generated.resources.dinner
import cherrytip.composeapp.generated.resources.empty
import cherrytip.composeapp.generated.resources.ic_add
import cherrytip.composeapp.generated.resources.ic_cancel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.InfoRecipeAction
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun DetailIngestionScreen(
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        BackWithTitle(stringResource(Res.string.dinner)) { }
        Information()
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            RecipeList(emptyList())
            ButtonAddMeal{}
        }
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
private fun RecipeList(recipes: List<RecipeEntity>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (recipes.isEmpty()) item { Texts.Option(text = stringResource(Res.string.empty), color = Colors.Black) }
        items(items = recipes, key = { it.id }) {
            RecipeItem(it) { }
        }
    }
}

@Composable
private fun RecipeItem(
    recipe: RecipeEntity,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Colors.PurpleBackground),
        colors = CardDefaults.cardColors(Colors.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            InfoRecipeAction(
                recipe = recipe,
                icon = painterResource(Res.drawable.ic_cancel)
            ) {
                onClick(recipe.id)
            }
        }
    }
}

@Composable
private fun Information() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Texts.Title(
                text = "508 / 889",
                fontSize = 16.sp
            )
            Texts.Light(
                text = "Calories",
                fontSize = 14.sp
            )
        }
        Row {
            ValueWithProperty(
                value = "40",
                property = "Protein"
            )
            ValueWithProperty(
                value = "200",
                property = "Carbs"
            )
            ValueWithProperty(
                value = "54",
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