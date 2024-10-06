package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.account
import cherrytip.composeapp.generated.resources.apply
import cherrytip.composeapp.generated.resources.gain_weight
import cherrytip.composeapp.generated.resources.goal_active
import cherrytip.composeapp.generated.resources.goal_calorie
import cherrytip.composeapp.generated.resources.goal_goal
import cherrytip.composeapp.generated.resources.lose_weight
import cherrytip.composeapp.generated.resources.save
import cherrytip.composeapp.generated.resources.stay_healthy
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveObjects
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalObjects
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType.Gain
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType.Lose
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType.Stay
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalButtons.Active
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalButtons.Calorie
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalButtons.Goal
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.DialogWrapper
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.SettingsItem
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents.ActiveItem
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun GoalScreen(
    component: GoalComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    var dialogItem by remember{ mutableStateOf<GoalButtons?>(null) }
    val state by component.state.collectAsState()
        Column(
            modifier = modifier.fillMaxSize().padding(20.dp)
        ) {
            BackWithTitle(stringResource(Res.string.account)) { component.clickBack() }
            Spacer(Modifier.height(30.dp))
            val screenState = state.goalState
            when (screenState) {
                is GoalStore.State.GoalState.Error -> {
                    ErrorContent(modifier = Modifier.weight(1f),text = screenState.text){
                        component.load()
                    }
                }
                GoalStore.State.GoalState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
                GoalStore.State.GoalState.Result -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        GoalButtons.entries.forEach { item ->
                            SettingsItem(
                                modifier = Modifier.fillMaxWidth(),
                                title = stringResource(item.stringRes),
                                value = when(item){
                                    Goal -> when(state.goal){
                                        Lose -> stringResource(Res.string.lose_weight)
                                        Stay -> stringResource(Res.string.stay_healthy)
                                        Gain -> stringResource(Res.string.gain_weight)
                                    }
                                    Active -> state.active.toString()
                                    Calorie -> "${state.calorie} kcal"
                                },
                                onClickable = item != Calorie,
                                onClick = {
                                    dialogItem = item
                                }
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Buttons.Gray(
                            onClick = {component.saveData()},
                            modifier = Modifier.fillMaxWidth().height(57.dp)
                        ){
                            Texts.Button(
                                text = stringResource(Res.string.save),
                                color = Colors.Purple
                            )
                        }
                    }
                }
                else -> {}
            }

        }
        val onDismissDialog = { dialogItem = null }
        val choiceDialogItem = dialogItem
        if (choiceDialogItem != null){
            DialogWrapper(
                onDismissDialog = onDismissDialog
            ){
                when(choiceDialogItem){
                    Goal -> DialogChangeGoal(state.goal){component.changeGoal(it); onDismissDialog()}
                    Active -> DialogChangeActive(state.active){component.changeActive(it); onDismissDialog()}
                    else -> {}
                }
            }
        }
    }

@Composable
private fun DialogChangeActive(
    active: ActiveType,
    onClickApply: (ActiveType) -> Unit
) {
    var selectedItem by remember { mutableStateOf(active) }
    val actives = ActiveObjects.entries
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        actives.forEach {
            ActiveItem(
                title = stringResource(it.titleRes),
                descr = stringResource(it.descrRes),
                isActive = selectedItem == it.type
            ){
                selectedItem = it.type
            }
        }
    }

    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickApply(selectedItem) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}


@Composable
private fun DialogChangeGoal(
    goal: GoalType,
    onClickApply: (GoalType) -> Unit
) {
    var selectedItem by remember { mutableStateOf(goal) }
    val goals = GoalObjects.entries
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        goals.forEach {
            val isSelected = selectedItem == it.goalType
            Buttons.Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    if (isSelected) Colors.Purple else Colors.PurpleBackground
                ),
                onClick = {
                    selectedItem = it.goalType
                },
                contentPadding = PaddingValues(18.dp)
            ) {
                SurveyComponents.IconWithText(
                    icon = painterResource(it.iconRes),
                    text = stringResource(it.textRes),
                    color = if (isSelected) Colors.White else Colors.Purple,
                )
            }
        }
    }

    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickApply(selectedItem) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}

private enum class GoalButtons(
    val stringRes: StringResource
) {

    Goal(Res.string.goal_goal),
    Active(Res.string.goal_active),
    Calorie(Res.string.goal_calorie),

}
