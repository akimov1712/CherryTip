package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.how_active_you
import cherrytip.composeapp.generated.resources.start_now
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents.ActiveItem

@Composable
fun ActiveFragmentContent(
    modifier: Modifier = Modifier,
    active: ActiveType,
    onClickBack: () -> Unit,
    onClickStart: (ActiveType) -> Unit
) {
    SurveyComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.how_active_you)
    ){
        var selectedType = remember { mutableStateOf(active) }
        ActiveList(
            modifier = Modifier.fillMaxWidth(),
            selectedType = selectedType,
        )
        Spacer(Modifier.weight(1f))
        SurveyComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickNext = { onClickStart(selectedType.value) },
            nextButtonText = stringResource(Res.string.start_now)
        )
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun ActiveList(
    modifier: Modifier = Modifier,
    selectedType: MutableState<ActiveType>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val actives = ActiveObjects.entries
        actives.forEach {
            ActiveItem(
                title = stringResource(it.titleRes),
                descr = stringResource(it.descrRes),
                isActive = selectedType.value == it.type
            ){
                selectedType.value = it.type
            }
        }
    }
}
