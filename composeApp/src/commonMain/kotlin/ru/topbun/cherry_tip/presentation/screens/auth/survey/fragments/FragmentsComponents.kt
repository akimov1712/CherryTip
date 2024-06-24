package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.continue_string
import cherrytip.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

object FragmentsComponents{

    @Composable
    fun ButtonsNavigation(onClickBack: () -> Unit, onClickContinue: () -> Unit, onEnableContinue: Boolean = true) {
        Row {
            Buttons.Purple(
                modifier = Modifier.size(60.dp),
                onClick = onClickBack
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = null,
                    tint = Colors.White
                )
            }
            Spacer(Modifier.width(10.dp))
            Buttons.Purple(
                modifier = Modifier.height(60.dp).fillMaxWidth(),
                onClick = onClickContinue,
                enabled = onEnableContinue,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Purple,
                    disabledContainerColor = Colors.Purple.copy(alpha = 0.3f)
                )
            ) {
                Texts.Button(
                    stringResource(Res.string.continue_string),
                )
            }
        }
    }

    @Composable
    fun IconWithText(
        icon: Painter,
        text: String,
        color: Color
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            contentDescription = text,
            tint = color
        )
        Spacer(Modifier.width(12.dp))
        Texts.Button(text, color = color, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
    }

    @Composable
    fun FragmentWrapper(
        modifier: Modifier = Modifier,
        title: String,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texts.Title(title)
            Spacer(Modifier.height(40.dp))
            content()
        }
    }

    @Composable
    fun TextField(
        modifier: Modifier = Modifier,
        text: String,
        onValueChange: (String) -> Unit,
        placeholder: String = "",
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextStyle(
            color = Colors.GrayDark,
            fontSize = 40.sp,
            fontFamily = Fonts.hovesMedium,
            textAlign = TextAlign.Center
        ),
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = true,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        minLines: Int = 1,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onTextLayout: (TextLayoutResult) -> Unit = {},
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        cursorBrush: Brush = SolidColor(Colors.GrayDark),
    ) {
        Box (
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if (text.isBlank()) Texts.Placeholder(placeholder)
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation,
                onTextLayout = onTextLayout,
                interactionSource = interactionSource,
                cursorBrush = cursorBrush,
            )
        }
    }


}