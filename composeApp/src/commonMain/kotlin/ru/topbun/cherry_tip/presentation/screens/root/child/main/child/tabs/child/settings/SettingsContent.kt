package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ProfileScreen(
    component: SettingsComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val state by component.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        state.items.forEach {
            ProfileItem(it){
                when(it){
                    Settings.Account -> component.clickAccount()
                    Settings.Profile -> component.clickProfile()
                    Settings.Goals -> component.clickGoals()
                    Settings.Units -> component.clickUnits()
                }
            }
        }
    }
}

@Composable
private fun ProfileItem(
    profile: Settings,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit
) {
    Buttons.Gray(
        onClick = onClickItem,
        modifier = modifier
            .fillMaxWidth()
            .height(57.dp),
        shape = RoundedCornerShape(13.dp),
        contentPadding = PaddingValues(horizontal = 18.dp)
    ){
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(profile.icon),
                contentDescription = null,
                tint = Colors.Purple
            )
            Spacer(Modifier.width(10.dp))
            Texts.Option(text = stringResource(profile.title))
        }
    }
}