package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            .size(60.dp)
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