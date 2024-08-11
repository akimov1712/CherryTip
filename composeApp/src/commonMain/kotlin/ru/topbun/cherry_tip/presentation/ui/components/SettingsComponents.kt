package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun SettingsItem(
    title: String,
    value: String,
    onClickable: Boolean = true,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val modifier = if (onClickable) modifier.clickable( indication = null, interactionSource = MutableInteractionSource()) {
        onClick?.let { it() }
    } else modifier
    ItemWrapper (modifier = modifier){
        Texts.Option(
            text = title,
            color = Colors.Black,
            fontSize = 16.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Texts.Option(
                text = value,
                color = Colors.Gray,
                fontSize = 16.sp
            )
            if (onClickable){
                Spacer(Modifier.width(7.dp))
                Icon(
                    modifier = Modifier.rotate(180f),
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = null,
                    tint = Colors.Gray
                )
            }
        }
    }
}

@Composable
private fun ItemWrapper(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row (
        modifier = modifier.fillMaxWidth().height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}
