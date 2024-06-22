package ru.topbun.cherry_tip.presentation.ui.utills

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.animateWrapContentHeight(
    isAnimate: Boolean,
    animationSpec: AnimationSpec<Dp> = tween(500)
): Modifier {
    var boxHeight by remember { mutableStateOf(0.dp) }
    val animateBoxHeight by animateDpAsState(
        targetValue = if (isAnimate) boxHeight else 0.dp,
        animationSpec
    )
    return this
        .then(
            if (isAnimate || boxHeight != 0.dp) Modifier.height(animateBoxHeight)
            else Modifier.wrapContentHeight()
        )
        .onGloballyPositioned {
            val height = it.size.height
            if (boxHeight == 0.dp){
                boxHeight = height.dp /2
            }
        }
}