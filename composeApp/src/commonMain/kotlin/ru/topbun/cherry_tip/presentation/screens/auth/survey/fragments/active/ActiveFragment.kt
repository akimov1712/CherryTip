package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.active

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.how_active_you
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.FragmentsComponents
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal.Goal
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal.GoalObjects
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ActiveFragmentContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit
) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.how_active_you)
    ){
        val actives = listOf(ActiveObjects.Inactive, ActiveObjects.Moderate, ActiveObjects.Active)
        var selectedType = remember { mutableStateOf(ActiveType.MODERATE) }
        ActiveList(
            modifier = Modifier.fillMaxWidth(),
            actives = actives,
            selectedType = selectedType,
        )
        Spacer(Modifier.weight(1f))
        FragmentsComponents.ButtonsNavigation(onClickBack, onClickContinue)
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun ActiveList(
    modifier: Modifier = Modifier,
    actives: List<ActiveObjects>,
    selectedType: MutableState<ActiveType>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
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

@Composable
private fun ActiveItem(
    modifier: Modifier = Modifier,
    title: String,
    descr: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(if (isActive) Colors.Purple else Colors.PurpleBackground),
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
        ){
            Texts.Option(
                text = title,
                color = if (isActive) Colors.White else Colors.Purple,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.height(10.dp))
            Texts.General(
                text = descr,
                color = if (isActive) Colors.GrayLight else Colors.Gray,
                textAlign = TextAlign.Start
            )
        }

    }
}