package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.account
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.logout
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ProfileAccountScreen(
    component: AccountComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        val state by component.state.collectAsState()
        BackWithTitle(stringResource(Res.string.account)){ component.clickBack() }
        Spacer(Modifier.height(30.dp))
        when(val screenState = state.profileState){
            is AccountStore.State.ProfileState.Error -> {
                Texts.Error(
                    modifier = Modifier.fillMaxSize(),
                    text = screenState.text,
                    color = Colors.Black,
                    textAlign = TextAlign.Center
                )
            }
            AccountStore.State.ProfileState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(color = Colors.Purple)
                }
            }
            is AccountStore.State.ProfileState.Result -> {
                Column{
                    ItemWrapper {
                        Texts.Option(
                            text = "User ID",
                            color = Colors.Black,
                            fontSize = 16.sp
                        )
                        Texts.Option(
                            text = screenState.userId,
                            color = Colors.Gray,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(Modifier.fillMaxWidth().height(3.dp).background(Colors.GrayLight))
                    ItemWrapper (
                        modifier = Modifier.clickable { component.logOut() }
                    ){
                        Texts.Option(
                            text = stringResource(Res.string.logout),
                            color = Colors.Black,
                            fontSize = 16.sp
                        )
                        Icon(
                            modifier = Modifier.rotate(180f),
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null,
                            tint = Colors.Purple
                        )
                    }
                }
            }
            else -> {}
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