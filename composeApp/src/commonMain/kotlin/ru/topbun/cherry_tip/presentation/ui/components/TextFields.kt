package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_drop_down
import cherrytip.composeapp.generated.resources.ic_search
import cherrytip.composeapp.generated.resources.not_selected
import cherrytip.composeapp.generated.resources.search
import coil3.compose.AsyncImagePainter.State.Empty.painter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts

object TextFields {

    val textStyle
        @Composable get() = TextStyle(
        color = Colors.Black,
        fontSize = 18.sp,
        fontFamily = Fonts.hovesMedium,
        textAlign = TextAlign.Start
    )

    @Composable
    fun OutlinedDropDownMenu(
        modifier: Modifier = Modifier,
        value: String,
        placeholderText: String,
        textStyle: TextStyle = this.textStyle.copy(textAlign = TextAlign.Start),
        isOpen: Boolean = false
    ) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholderText = placeholderText,
            textStyle = textStyle,
            singleLine = true,
            trailingIcon = {
                val animateRotate by animateFloatAsState(if (isOpen) 180f else 0f)
                Icon(
                    modifier = Modifier.rotate(animateRotate),
                    painter = painterResource(Res.drawable.ic_drop_down),
                    contentDescription = null,
                    tint = Colors.Purple
                )
            }
        )
    }

    @Composable
    fun Search(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier.defaultMinSize(minHeight = 60.dp).fillMaxWidth(),
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextFields.textStyle,
        label: @Composable (() -> Unit)? = null,
        placeholderText: String = stringResource(Res.string.search),
        leadingIcon: @Composable (() -> Unit)? = {
            Spacer(Modifier.width(12.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_search),
                contentDescription = stringResource(Res.string.search),
                tint = Colors.Black
            )
        },
        trailingIcon: @Composable (() -> Unit)? = null,
        prefix: @Composable (() -> Unit)? = null,
        suffix: @Composable (() -> Unit)? = null,
        supportingText: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = true,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        minLines: Int = 1,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape = RoundedCornerShape(13.dp),
        colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
            cursorColor = Colors.Purple,
            focusedBorderColor = Colors.PurpleBackground,
            unfocusedBorderColor = Colors.PurpleBackground,
            errorBorderColor = Colors.Red,
        ),
    ) = OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholderText = placeholderText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )

    @Composable
    fun OutlinedTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier.defaultMinSize(minHeight = 60.dp).fillMaxWidth(),
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextFields.textStyle,
        label: @Composable (() -> Unit)? = null,
        placeholderText: String = "",
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        prefix: @Composable (() -> Unit)? = null,
        suffix: @Composable (() -> Unit)? = null,
        supportingText: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = true,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        minLines: Int = 1,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape = RoundedCornerShape(13.dp),
        colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
            cursorColor = Colors.Purple,
            focusedBorderColor = Colors.PurpleBackground,
            unfocusedBorderColor = Colors.PurpleBackground,
            errorBorderColor = Colors.Red,
        ),
    ) = androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = { Texts.Option(
            text = placeholderText,
            color = Colors.Gray,
            textAlign = textStyle.textAlign,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        ) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )

}