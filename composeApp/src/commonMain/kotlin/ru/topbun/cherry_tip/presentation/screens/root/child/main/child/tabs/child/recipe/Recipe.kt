package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.carbs
import cherrytip.composeapp.generated.resources.fat
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_fire
import cherrytip.composeapp.generated.resources.protein
import cherrytip.composeapp.generated.resources.unknown
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBars
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.formatMinutesToTime

@Composable
fun RecipeItem(
    recipe: RecipeEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Colors.PurpleBackground),
        colors = CardDefaults.cardColors(Colors.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            InfoRecipe(recipe)
            Spacer(Modifier.height(16.dp))
            Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
            Spacer(Modifier.height(16.dp))
            Nutrients(recipe)
        }
    }
}

@Composable
private fun Nutrients(recipe: RecipeEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutrientsItem(
            text = stringResource(Res.string.protein),
            value = recipe.protein,
            progress = recipe.proteinPercent,
            progressColor = Colors.GreenLight
        )
        NutrientsItem(
            text = stringResource(Res.string.carbs),
            value = recipe.carbs,
            progress = recipe.carbsPercent,
            progressColor = Colors.Blue
        )
        NutrientsItem(
            text = stringResource(Res.string.fat),
            value = recipe.fat,
            progress = recipe.fatPercent,
            progressColor = Colors.Yellow
        )
    }
}

@Composable
private fun NutrientsItem(
    text: String,
    value: Int,
    progress: Float,
    progressColor: Color
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ProgressBars.Default(
            modifier = Modifier
                .size(66.dp, 10.dp)
                .clip(CircleShape),
            progress = progress,
            progressColor = progressColor,
            shapeProgress = CircleShape
        )
        Spacer(Modifier.height(10.dp))
        Texts.Option(
            text = "$value g",
            fontSize = 16.sp,
            color = Colors.Black
        )
        Spacer(Modifier.height(3.dp))
        Texts.Light(text)
    }
}

@Composable
private fun InfoRecipe(recipe: RecipeEntity) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RecipeImage(recipe)
        Spacer(Modifier.width(10.dp))
        Column {
            Texts.Option(
                text = recipe.title,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                color = Colors.Black
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                IconWithText(
                    text = recipe.cookingTime?.formatMinutesToTime() ?: stringResource(Res.string.unknown),
                    iconRes = Res.drawable.ic_clock,
                    contentDescription = "Time"
                )
                IconWithText(
                    text = recipe.calories?.let { "$it Kcal" } ?: stringResource(Res.string.unknown),
                    iconRes = Res.drawable.ic_fire,
                    contentDescription = "Kcal"
                )
            }
        }
    }
}

@Composable
private fun IconWithText(
    iconRes: DrawableResource,
    text: String,
    contentDescription: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = Colors.GrayDark
        )
        Spacer(Modifier.width(7.dp))
        Texts.Option(
            text = text,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            color = Colors.GrayDark
        )
    }
}

@Composable
private fun RecipeImage(recipe: RecipeEntity) {
    Box(
        modifier = Modifier.size(70.dp).clip(RoundedCornerShape(13.dp)),
        contentAlignment = Alignment.Center
    ) {
        var isLoading by rememberSaveable { mutableStateOf(true) }
        if (isLoading) CircularProgressIndicator(color = Colors.Purple, modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            onState = { isLoading = it is AsyncImagePainter.State.Loading },
            contentScale = ContentScale.Crop
        )
    }
}