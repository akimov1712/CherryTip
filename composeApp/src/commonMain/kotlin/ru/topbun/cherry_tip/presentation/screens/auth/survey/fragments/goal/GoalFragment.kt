package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.continue_string
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.what_your_goal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun GoalFragmentContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val goals = listOf(GoalItems.Lose, GoalItems.Stay, GoalItems.Gain)
        var selectedItem = remember { mutableStateOf(Goal.Lose) }

        Texts.Title(stringResource(Res.string.what_your_goal))
        Spacer(Modifier.height(40.dp))
        GoalList(goals, selectedItem)
        Spacer(Modifier.weight(1f))
        ButtonsNavigation(onClickBack, onClickContinue)
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun ButtonsNavigation(onClickBack: () -> Unit, onClickContinue: () -> Unit) {
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
            onClick = onClickContinue
        ) {
            Texts.Button(stringResource(Res.string.continue_string))
        }
    }
}

@Composable
private fun GoalList(
    goals: List<GoalItems>,
    selectedItem: MutableState<Goal>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        goals.forEach {
            val isSelected = selectedItem.value == it.goal
            Buttons.Default(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    if (isSelected) Colors.Purple else Colors.PurpleBackground
                ),
                onClick = {
                    selectedItem.value = it.goal
                },
                contentPadding = PaddingValues(18.dp)
            ) {
                IconWithText(
                    icon = painterResource(it.iconRes),
                    text = stringResource(it.textRes),
                    color = if (isSelected) Colors.White else Colors.Purple,
                )
            }
        }
    }
}

@Composable
private fun IconWithText(
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

