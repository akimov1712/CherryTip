package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.account
import cherrytip.composeapp.generated.resources.apply
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.save
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.GenderObjects
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.City
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.DateBirth
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.FirstName
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.LastName
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.Sex
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.DialogWrapper
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
    val snackbar = SnackbarHostState()
    var dialogItem by remember{ mutableStateOf<ProfileObjects?>(null) }
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
            val screenState = state.profileState
            when (screenState) {
                is ProfileStore.State.ProfileState.Error -> {
                    rememberCoroutineScope().launch {
                        snackbar.showSnackbar(screenState.text)
                    }
                }
                ProfileStore.State.ProfileState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
                else -> {}
            }
            if (screenState != ProfileStore.State.ProfileState.Loading){
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ProfileObjects.entries.forEach { item ->
                        ProfileItem(
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

}

@Composable
private fun DialogChangeBirth(
    date: GMTDate,
    onClickSave: (GMTDate) -> Unit
) {
    var date by remember { mutableStateOf(date) }
    SurveyComponents.WheelDatePicker(date.toLocalDate()){
        date = it.toGMTDate()
    }
    Spacer(Modifier.height(32.dp))
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(57.dp),
        onClick = { onClickSave(date) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}


@Composable
private fun DialogChangeGender(
    gender: Gender,
    onClickSave: (Gender) -> Unit
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
        onClick = { onClickSave(selectedItem) }
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
    onClickSave: (String) -> Unit
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
        onClick = { onClickSave(text) }
    ){
        Texts.Button(
            text = stringResource(Res.string.apply)
        )
    }
}


@Composable
private fun ProfileItem(
    title: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ItemWrapper (modifier = modifier.clickable( indication = null, interactionSource = MutableInteractionSource()) {
        onClick()
    }){
        Texts.Option(
            text = title,
            color = Colors.Black,
            fontSize = 16.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Texts.Option(
                text = value,
                color = Colors.Gray,
                fontSize = 16.sp
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                modifier = Modifier.rotate(180f),
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = null,
                tint = Colors.Gray
            )
        }
    }
}

@Composable
private fun ItemWrapper(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row (
        modifier = modifier.fillMaxWidth().height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content
    )
}
