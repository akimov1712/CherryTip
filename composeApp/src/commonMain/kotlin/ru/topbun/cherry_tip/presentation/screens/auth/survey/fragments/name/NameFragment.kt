package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import cherrytip.composeapp.generated.resources.what_your_gender
import cherrytip.composeapp.generated.resources.what_your_name
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.FragmentsComponents
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun NameFragmentContent(modifier: Modifier = Modifier, onClickContinue: (String) -> Unit) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_name)
    ){
        var text by rememberSaveable {
            mutableStateOf("")
        }
        FragmentsComponents.TextField(
            text = text,
            onValueChange = { if(it.length <= 16) text = it },
            placeholder = "John"
        )
        Spacer(Modifier.weight(1f))
        Buttons.Purple(
            modifier = Modifier.height(60.dp).fillMaxWidth(),
            onClick = {
                onClickContinue(text)
            }
        ){
            Texts.Button(stringResource(Res.string.continue_string))
        }
        Spacer(Modifier.height(20.dp))
    }
}