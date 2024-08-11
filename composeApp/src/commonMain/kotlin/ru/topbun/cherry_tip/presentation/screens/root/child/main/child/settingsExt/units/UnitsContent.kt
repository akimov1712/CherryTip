package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
import cherrytip.composeapp.generated.resources.units_blood_glucose
import cherrytip.composeapp.generated.resources.units_height
import cherrytip.composeapp.generated.resources.units_target_weight
import cherrytip.composeapp.generated.resources.units_weight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsButtons.*
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.DialogWrapper
import ru.topbun.cherry_tip.presentation.ui.components.SettingsItem
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents.ActiveItem
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun UnitsScreen(
    component: UnitsComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val snackbar = SnackbarHostState()
    var dialogItem by remember{ mutableStateOf<UnitsButtons?>(null) }
    val state by component.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbar) }
    ){
        Column(
            modifier = modifier.fillMaxSize().padding(20.dp)
        ) {
            BackWithTitle(stringResource(Res.string.account)) { component.clickBack() }
            Spacer(Modifier.height(30.dp))
            val screenState = state.unitsState
            when (screenState) {
                is UnitsStore.State.UnitsState.Error -> {
                    rememberCoroutineScope().launch {
                        snackbar.showSnackbar(screenState.text)
                    }
                }
                UnitsStore.State.UnitsState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
                else -> {}
            }
            if (screenState != UnitsStore.State.UnitsState.Loading){
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    UnitsButtons.entries.forEach { item ->
                        SettingsItem(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(item.stringRes),
                            value = when(item){
                                Weight -> "${state.weight} kg"
                                TargetWeight -> "${state.targetWeight} kg"
                                Height -> "${state.height} cm"
                                BloodGlucose -> "${state.bloodGlucose} mg/dL"
                            },
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

        }
        val onDismissDialog = { dialogItem = null }
        val choiceDialogItem = dialogItem
        if (choiceDialogItem != null){
            DialogWrapper(
                onDismissDialog = onDismissDialog
            ){
                when(choiceDialogItem){
                    Weight -> DialogChangeNumber(
                        number = state.weight,
                        range = 0..300
                    ){ component.changeWeight(it); onDismissDialog()}
                    TargetWeight -> DialogChangeNumber(
                        number = state.targetWeight,
                        range = 0..300
                    ){ component.changeTargetWeight(it); onDismissDialog()}
                    Height -> DialogChangeNumber(
                        number = state.height,
                        range = 0..250
                    ){ component.changeHeight(it); onDismissDialog()}
                    BloodGlucose -> DialogChangeNumber(
                        number = state.bloodGlucose,
                        range = 0..300
                    ){ component.changeBloodGlucose(it); onDismissDialog()}
                }
            }
        }
    }
}

@Composable
private fun DialogChangeNumber(
    number: Int,
    placeholder: Int = 0,
    range: IntRange,
    onClickApply: (Int) -> Unit
) {
    var number by remember{
        mutableStateOf(number.toString())
    }
    SurveyComponents.TextField(
        text = number,
        onValueChange = {
            if (it.isBlank()) number = ""
            else if (it.toInt() in range) number = it
        },
        placeholder = placeholder.toString(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickApply(number.toInt()) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}

private enum class UnitsButtons(
    val stringRes: StringResource
) {

    Weight(Res.string.units_weight),
    TargetWeight(Res.string.units_target_weight),
    Height(Res.string.units_height),
    BloodGlucose(Res.string.units_blood_glucose),

}
