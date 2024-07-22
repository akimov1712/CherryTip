package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun CustomTabRow(
    selectedIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onSelectedChange: (Int) -> Unit
) {

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
            .size(57.dp)
            .background(Colors.PurpleBackground, RoundedCornerShape(16.dp))
            .padding(vertical = 5.dp, horizontal = 2.dp),
        indicator = {},
        divider = {}
    ) {
        items.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            val background = if (selected) Colors.Purple else Colors.Transparent
            val textColor = if (selected) Colors.White else Colors.Gray
            Tab(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(background),
                selected = selected,
                onClick = { onSelectedChange(index) },
                text = { Texts.Button(text = text, fontSize = 16.sp, color = textColor) }
            )
        }
    }
}