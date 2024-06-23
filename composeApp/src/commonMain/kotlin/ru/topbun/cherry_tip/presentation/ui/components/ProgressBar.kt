package ru.topbun.cherry_tip.presentation.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ru.topbun.cherry_tip.presentation.ui.Colors

object ProgressBar{

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

}