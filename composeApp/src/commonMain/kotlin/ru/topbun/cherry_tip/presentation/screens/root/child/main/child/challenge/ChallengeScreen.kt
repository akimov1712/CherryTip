package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements.ChallengeItem
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ChallengeScreen(modifier: Modifier = Modifier.statusBarsPadding()) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        var selectedIndex by rememberSaveable { mutableStateOf(0) }
        val items = listOf("All", "Active")
        BackWithTitle()
        Spacer(Modifier.height(30.dp))
        CustomTabRow(
            items = items,
            selectedIndex = selectedIndex
        ){ selectedIndex = it }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(20){
                item { ChallengeItem {  } }
            }
        }
    }
}

@Composable
private fun BackWithTitle() {
    Row {
        Icon(
            painter = painterResource(Res.drawable.ic_back),
            contentDescription = null,
            tint = Colors.Purple
        )
        Spacer(Modifier.width(10.dp))
        Texts.Title(text = stringResource(Res.string.challenges))
    }
}

