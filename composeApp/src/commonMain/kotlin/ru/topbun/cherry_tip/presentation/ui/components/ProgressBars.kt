package ru.topbun.cherry_tip.presentation.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
    fun FullscreenLoader(modifier: Modifier = Modifier) {
        Box(modifier.fillMaxSize().background(Colors.Black.copy(0.1f)), contentAlignment = Alignment.Center){
            Card(
                modifier = Modifier.padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Colors.White
                )
            ){
                CircularProgressIndicator(color = Colors.Purple)
            }
        }
    }

}