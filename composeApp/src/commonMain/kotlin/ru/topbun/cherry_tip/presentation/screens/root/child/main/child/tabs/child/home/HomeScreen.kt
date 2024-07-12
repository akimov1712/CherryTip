package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_peach
import cherrytip.composeapp.generated.resources.nutrition_tips
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun HomeContent(modifier: Modifier = Modifier.statusBarsPadding()) {
    Column(
        modifier = modifier.padding(vertical = 24.dp, horizontal = 20.dp)
    ) {
        Texts.Title(stringResource(Res.string.nutrition_tips), textAlign = TextAlign.Start)
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconWithText()
        }
    }
}

@Composable
private fun IconWithText() {
    Column {
        Card(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Colors.PurpleBackground),
            colors = CardDefaults.cardColors(containerColor = Colors.Transparent),
            onClick = {}
        ){
            Icon(modifier = Modifier.padding(20.dp), painter = painterResource(Res.drawable.ic_peach), contentDescription = null)
        }
        Spacer(Modifier.height(7.dp))
        Texts.Tips("Fruits &\n" +
                "Vegetables")
    }
}