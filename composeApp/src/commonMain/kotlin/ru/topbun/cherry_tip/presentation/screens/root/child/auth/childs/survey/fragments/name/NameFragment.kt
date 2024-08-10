package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.name

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.what_your_name
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents

@Composable
fun NameFragmentContent(
    modifier: Modifier = Modifier,
    name: String,
    onClickContinue: (String) -> Unit
) {
    SurveyComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_name)
    ) {
        var text by rememberSaveable {
            mutableStateOf(name)
        }
        SurveyComponents.TextField(
            text = text,
            onValueChange = { if (it.length <= 20) text = it },
            placeholder = "John"
        )
        Spacer(Modifier.weight(1f))
        SurveyComponents.ButtonsNavigation(
            onClickNext = {
                onClickContinue(text)
            },
            isEnableBack = false
        )
        Spacer(Modifier.height(20.dp))
    }
}