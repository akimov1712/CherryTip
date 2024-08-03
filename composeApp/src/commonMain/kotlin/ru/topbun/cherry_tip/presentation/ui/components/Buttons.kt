package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import ru.topbun.cherry_tip.presentation.ui.Colors

object Buttons {

    @Composable
    fun Gray(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = null,
        shape: Shape = RoundedCornerShape(16.dp),
        border: BorderStroke? = null,
        colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Colors.PurpleBackground),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        this.Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            interactionSource = interactionSource,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPadding,
            content = content,
        )
    }

    @Composable
    fun Purple(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = null,
        shape: Shape = RoundedCornerShape(16.dp),
        border: BorderStroke? = null,
        colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Colors.Purple, disabledContainerColor = Colors.Purple.copy(0.5f)),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        this.Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            interactionSource = interactionSource,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPadding,
            content = content,
        )
    }

    @Composable
    fun Button(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = null,
        shape: Shape = RoundedCornerShape(16.dp),
        border: BorderStroke? = null,
        containerColor: Color = Color.Unspecified,
        colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = containerColor, disabledContainerColor = containerColor),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        val rippleColor = Colors.Gray
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = modifier.clip(shape).indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = rippleColor,
                )
            ),
            enabled = enabled,
            interactionSource = interactionSource,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPadding,
            content = content,
        )
    }

    @Composable
    fun Icon(
        icon: Painter,
        modifier: Modifier = Modifier,
        contentColor: Color = Colors.Purple,
        containerColor: Color = Colors.White,
        shape: Shape = RoundedCornerShape(16.dp),
        border: BorderStroke? = BorderStroke(2.dp, Colors.PurpleBackground),
        onClick: () -> Unit
    ) {
        Button(
            modifier = modifier,
            onClick = onClick,
            shape = shape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = containerColor),
            border = border
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = contentColor
            )
        }
    }

    @Composable
    fun BackWithTitle(text: String, clickBack: () -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Buttons.Icon(
                modifier = Modifier.size(24.dp),
                onClick = clickBack,
                icon = painterResource(Res.drawable.ic_back),
                containerColor = Colors.Transparent,
                contentColor = Colors.Purple,
                shape = CircleShape,
                border = null
            )
            Spacer(Modifier.width(10.dp))
            Texts.Title(text = text)
        }
    }

}