package ru.topbun.cherry_tip.presentation.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.topbun.cherry_tip.presentation.ui.Colors

object ProgressBars{

    @Composable
    fun Default(
        @FloatRange(0.0,1.0) progress: Float,
        modifier: Modifier = Modifier,
        backgroundColor: Color = Colors.PurpleBackground,
        progressColor: Color = Colors.Purple,
        shapeProgress: Shape
    ) {
        Box(
            modifier = modifier.background(backgroundColor)
        ){
            val animWidth by animateFloatAsState(progress, tween(150, easing = LinearOutSlowInEasing))
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animWidth)
                    .background(progressColor, shapeProgress)
            )
        }
    }

    @Composable
    fun CropProgressIndicator(
        modifier: Modifier = Modifier,
        @FloatRange(0.0, 1.0) value: Float,
        @FloatRange(0.0, 1.0) percentFillRound: Float,
        strokeWidth: Dp,
        color: Color = Colors.Purple,
        trackColor: Color = Colors.PurpleBackground,
        strokeCap: StrokeCap = StrokeCap.Round
    ) {
        val progressValue = value * percentFillRound
        val rotateDegrees = 180f + (360 * (1 - percentFillRound)) / 2
        val animProgress by animateFloatAsState(
            targetValue = progressValue,
            animationSpec = tween(15000, easing = LinearOutSlowInEasing)
        )
        CircularProgressIndicator(
            modifier = modifier.rotate(rotateDegrees),
            progress = { percentFillRound },
            color = trackColor,
            strokeWidth = strokeWidth,
            strokeCap = strokeCap
        )
        CircularProgressIndicator(
            modifier = modifier.rotate(rotateDegrees),
            progress = { animProgress },
            color = color,
            strokeWidth = strokeWidth,
            strokeCap = strokeCap
        )
    }

}