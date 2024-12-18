package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.targetWeight

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.what_your_target_weight
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents

@Composable
fun TargetWeightFragmentContent(
    modifier: Modifier = Modifier,
    targetWeight: Int,
    onClickBack: () -> Unit,
    onClickContinue: (Int) -> Unit,
) {
    SurveyComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_target_weight)
    ){
        var weight by rememberSaveable{ mutableStateOf(targetWeight) }
        Spacer(Modifier.weight(1f))
        SurveyComponents.NumberSlidePicker(
            modifier = Modifier.padding(horizontal = 20.dp),
            minValue = 20,
            maxValue = 300,
            startValue = weight,
            unit = "kg"
        ){
            weight = it
        }
        Spacer(Modifier.weight(1f))
        SurveyComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickNext = { onClickContinue(weight) }
        )
        Spacer(Modifier.height(20.dp))
    }
}