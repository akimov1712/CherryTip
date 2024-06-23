package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.continue_string
import cherrytip.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
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


}