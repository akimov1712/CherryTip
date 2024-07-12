package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.what_your_goal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.FragmentsComponents
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons

@Composable
fun GoalFragmentContent(
    modifier: Modifier = Modifier,
    goal: GoalType,
    onClickBack: () -> Unit,
    onClickContinue: (GoalType) -> Unit
) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_goal)
    ){
        val goals = listOf(GoalObjects.Lose, GoalObjects.Stay, GoalObjects.Gain)
        var selectedItem = remember { mutableStateOf(goal) }

        GoalList(goals, selectedItem)
        Spacer(Modifier.weight(1f))
        FragmentsComponents.ButtonsNavigation(onClickBack, { onClickContinue(selectedItem.value) } )
        Spacer(Modifier.height(20.dp))
    }
}


@Composable
private fun GoalList(
    goals: List<GoalObjects>,
    selectedItem: MutableState<GoalType>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        goals.forEach {
            val isSelected = selectedItem.value == it.goalType
            Buttons.Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    if (isSelected) Colors.Purple else Colors.PurpleBackground
                ),
                onClick = {
                    selectedItem.value = it.goalType
                },
                contentPadding = PaddingValues(18.dp)
            ) {
                FragmentsComponents.IconWithText(
                    icon = painterResource(it.iconRes),
                    text = stringResource(it.textRes),
                    color = if (isSelected) Colors.White else Colors.Purple,
                )
            }
        }
    }
}

