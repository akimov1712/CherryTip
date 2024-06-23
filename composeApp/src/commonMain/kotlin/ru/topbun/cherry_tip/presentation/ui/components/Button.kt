package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ru.topbun.cherry_tip.presentation.ui.Colors

object Button {

    @Composable
    fun Gray(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        elevation: ButtonElevation? = null,
        shape: Shape = RoundedCornerShape(16.dp),
        border: BorderStroke? = null,
        colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Colors.PurpleBackground),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        Button(
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
        colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Colors.Purple),
        contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        Button(
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

}