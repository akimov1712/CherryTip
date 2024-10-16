package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.height

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.what_your_height
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents

@Composable
fun HeightFragmentContent(
    modifier: Modifier = Modifier,
    height: Int,
    onClickBack: () -> Unit,
    onClickContinue: (Int) -> Unit,
) {
    SurveyComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_height)
    ){
        var height by rememberSaveable{ mutableStateOf(height) }
        Spacer(Modifier.weight(1f))
        SurveyComponents.NumberSlidePicker(
            modifier = Modifier.fillMaxWidth(),
            minValue = 100,
            maxValue = 250,
            startValue = height,
            unit = "cm"
        ){
            height = it
        }
        Spacer(Modifier.height(20.dp))
        SurveyComponents.WarningAboutChangingValues()
        Spacer(Modifier.weight(1f))
        SurveyComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickNext = { onClickContinue(height) }
        )
        Spacer(Modifier.height(20.dp))
    }
}
