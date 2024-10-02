package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.carbs
import cherrytip.composeapp.generated.resources.fat
import cherrytip.composeapp.generated.resources.protein
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.ui.Colors


@Composable
fun Nutrients(recipe: RecipeEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
fun RowScope.NutrientsItem(
    text: String,
    value: String,
    progress: Float,
    progressColor: Color
) {
    Column(
        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
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
            text = value,
            fontSize = 16.sp,
            color = Colors.Black
        )
        Spacer(Modifier.height(3.dp))
        Texts.Light(text)
    }
}

@Composable
fun RowScope.NutrientsItem(
    text: String,
    value: Int,
    progress: Float,
    progressColor: Color
) {
    Column(
        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
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
