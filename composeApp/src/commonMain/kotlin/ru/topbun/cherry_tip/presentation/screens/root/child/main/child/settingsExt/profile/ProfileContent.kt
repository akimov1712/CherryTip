package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import androidx.compose.foundation.background
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
import cherrytip.composeapp.generated.resources.profile_city
import cherrytip.composeapp.generated.resources.profile_date_birth
import cherrytip.composeapp.generated.resources.profile_first_name
import cherrytip.composeapp.generated.resources.profile_last_name
import cherrytip.composeapp.generated.resources.profile_sex
import cherrytip.composeapp.generated.resources.save
import cherrytip.composeapp.generated.resources.settings_profile
import io.ktor.util.date.GMTDate
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.GenderObjects
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileButtons.City
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileButtons.DateBirth
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileButtons.FirstName
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileButtons.LastName
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileButtons.Sex
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.DialogWrapper
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.SettingsItem
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.formatToString
import ru.topbun.cherry_tip.utills.toGMTDate
import ru.topbun.cherry_tip.utills.toLocalDate

@Composable
fun ProfileScreen(
    component: ProfileComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    var dialogItem by remember{ mutableStateOf<ProfileButtons?>(null) }
    val state by component.state.collectAsState()
        Column(
            modifier = modifier.fillMaxSize().padding(20.dp)
        ) {
            BackWithTitle(stringResource(Res.string.settings_profile)) { component.clickBack() }
            Spacer(Modifier.height(30.dp))
            val screenState = state.profileState
            when (screenState) {
                is ProfileStore.State.ProfileState.Error -> {
                    ErrorContent(modifier = Modifier.weight(1f), text = screenState.text) {
                        component.load()
                    }
                }

                ProfileStore.State.ProfileState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
                ProfileStore.State.ProfileState.Result -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        ProfileButtons.entries.forEach { item ->
                            SettingsItem(
                                modifier = Modifier.fillMaxWidth(),
                                title = stringResource(item.title),
                                value = when(item){
                                    FirstName -> state.name
                                    LastName -> state.surname
                                    City -> state.city
                                    Sex -> state.gender.name
                                    DateBirth -> state.birth.formatToString()
                                },
                                onClick = {
                                    dialogItem = item
                                }
                            )
                            Spacer(Modifier.fillMaxWidth().height(1.dp).background(Colors.GrayLight))
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
                    FirstName -> DialogChangeText(text = state.name, placeholder = "John"){
                        component.changeName(it); onDismissDialog()
                    }
                    LastName -> DialogChangeText(text = state.surname, placeholder = "Smith"){
                        component.changeSurname(it); onDismissDialog()
                    }
                    City -> DialogChangeText(text = state.city, placeholder = "Moscow"){
                        component.changeCity(it); onDismissDialog()
                    }
                    Sex -> DialogChangeGender(gender = state.gender){
                        component.changeGender(it); onDismissDialog()
                    }
                    DateBirth -> DialogChangeBirth(date = state.birth){
                        component.changeBirth(it); onDismissDialog()
                    }
                }

            }
        }
    }

@Composable
private fun DialogChangeBirth(
    date: GMTDate,
    onClickApply: (GMTDate) -> Unit
) {
    var date by remember { mutableStateOf(date) }
    SurveyComponents.WheelDatePicker(date.toLocalDate()){
        date = it.toGMTDate()
    }
    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickApply(date) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}


@Composable
private fun DialogChangeGender(
    gender: Gender,
    onClickApply: (Gender) -> Unit
) {
    var selectedItem by remember {  mutableStateOf(gender) }
    val items = listOf(GenderObjects.Male, GenderObjects.Female)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach {
            val isSelected = selectedItem == it.gender
            Buttons.Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    if (isSelected) Colors.Purple else Colors.PurpleBackground
                ),
                onClick = {
                    selectedItem = it.gender
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


@Composable
private fun DialogChangeText(
    text: String,
    placeholder: String = "John",
    onClickApply: (String) -> Unit
) {
    var text by remember{
        mutableStateOf(text)
    }
    SurveyComponents.TextField(
        text = text,
        onValueChange = { if (it.length <= 20) text = it },
        placeholder = placeholder
    )
    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickApply(text) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}

private enum class ProfileButtons(
    val title: StringResource
) {

    FirstName(Res.string.profile_first_name),
    LastName(Res.string.profile_last_name),
    City(Res.string.profile_city),
    Sex(Res.string.profile_sex),
    DateBirth(Res.string.profile_date_birth),

}