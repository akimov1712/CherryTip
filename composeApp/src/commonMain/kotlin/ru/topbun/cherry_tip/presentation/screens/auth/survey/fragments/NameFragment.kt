package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.continue_string
import cherrytip.composeapp.generated.resources.what_your_name
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts
import ru.topbun.cherry_tip.presentation.ui.components.Button
import ru.topbun.cherry_tip.presentation.ui.components.Text

@Composable
fun NameFragmentContent(modifier: Modifier = Modifier, onClickContinue: (String) -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text.Title(stringResource(Res.string.what_your_name))
        Spacer(Modifier.height(40.dp))
        var text by rememberSaveable {
            mutableStateOf("")
        }
        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if (text.isBlank()) Text.Placeholder("John")
            BasicTextField(
                value = text,
                onValueChange = {if(it.length <= 16) text = it},
                singleLine = true,
                textStyle = TextStyle(
                    color = Colors.GrayDark,
                    fontSize = 40.sp,
                    fontFamily = Fonts.hovesMedium,
                    textAlign = TextAlign.Center
                ),
                cursorBrush = SolidColor(Colors.Purple),
            )
        }
        Spacer(Modifier.weight(1f))
        Button.Purple(
            modifier = Modifier.height(60.dp).fillMaxWidth(),
            onClick = {
                onClickContinue(text)
            }
        ){
            Text.Button(stringResource(Res.string.continue_string))
        }
        Spacer(Modifier.height(20.dp))
    }
}