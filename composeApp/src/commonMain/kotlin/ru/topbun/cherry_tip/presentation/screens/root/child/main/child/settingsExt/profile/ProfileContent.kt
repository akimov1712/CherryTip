package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.account
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.logout
import cherrytip.composeapp.generated.resources.save
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountStore
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileObjects.*
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.formatToString

@Composable
fun ProfileScreen(
    component: ProfileComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        val state by component.state.collectAsState()
        var isShowDialog by rememberSaveable{ mutableStateOf(false) }
        BackWithTitle(stringResource(Res.string.account)) { component.clickBack() }
        Spacer(Modifier.height(30.dp))
        when (val screenState = state.profileState) {
            is ProfileStore.State.ProfileState.Error -> {
                Texts.Error(
                    modifier = Modifier.fillMaxSize(),
                    text = screenState.text,
                    color = Colors.Black,
                    textAlign = TextAlign.Center
                )
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
                                when (item) {
                                    FirstName -> {isShowDialog = true}
                                    LastName -> {}
                                    City -> {}
                                    Sex -> {}
                                    DateBirth -> {}
                                }
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
                if (isShowDialog){
                    Dialog(
                        onDismissRequest = {isShowDialog = false},
                        properties = DialogProperties()
                    ){
                        Box(Modifier.size(300.dp).background(color = Colors.White, RoundedCornerShape(30.dp)))
                    }
                }
            }
            else -> {}
        }
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
