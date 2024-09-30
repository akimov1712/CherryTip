package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.calendar
import cherrytip.composeapp.generated.resources.ic_calendar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header()
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Texts.Title(stringResource(Res.string.calendar))
        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_calendar),
                contentDescription = null,
                tint = Colors.Purple
            )
        }
    }
}