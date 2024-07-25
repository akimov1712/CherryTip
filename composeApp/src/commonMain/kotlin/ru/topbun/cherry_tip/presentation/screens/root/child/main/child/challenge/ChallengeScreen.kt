package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_lightning
import cherrytip.composeapp.generated.resources.img_test
import cherrytip.composeapp.generated.resources.more
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ChallengeScreen(
    component: ChallengeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        var selectedIndex by rememberSaveable { mutableStateOf(0) }
        val items = listOf("All", "Active")
        BackWithTitle{ component.clickBack() }
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
                item { ChallengeItem { component.openChallengeDetail() } }
            }
        }
    }
}

@Composable
private fun BackWithTitle(clickBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Buttons.Icon(
            modifier = Modifier.size(24.dp),
            onClick = clickBack,
            icon = painterResource(Res.drawable.ic_back),
            containerColor = Colors.Transparent,
            contentColor = Colors.Purple,
            shape = CircleShape,
            border = null
        )
        Spacer(Modifier.width(10.dp))
        Texts.Title(text = stringResource(Res.string.challenges))
    }
}

@Composable
private fun ChallengeItem(onClickMore: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFB7CE))
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            InfoChallenge(modifier = Modifier.weight(1f), onClickMore)
            Image(
                modifier = Modifier.weight(1f).align(Alignment.Bottom).scale(1.15f),
                painter = painterResource(Res.drawable.img_test),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun InfoChallenge(modifier: Modifier, onClickMore: () -> Unit) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Texts.Option("Sweet Free", color = Colors.Black)
        IconWithText(painter = painterResource(Res.drawable.ic_clock), text = "7 days")
        IconWithText(painter = painterResource(Res.drawable.ic_lightning), text = "Medium")
        Buttons.Button(
            onClick = onClickMore,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Colors.White),
            contentPadding = PaddingValues(15.dp, 10.dp),
        ) {
            Texts.Button(
                stringResource(Res.string.more),
                color = Colors.Black,
                fontSize = 14.sp
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                modifier = Modifier.rotate(180f).size(16.dp),
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = null,
                tint = Colors.Black
            )
        }
    }
}

@Composable
private fun IconWithText(
    painter: Painter,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painter, contentDescription = null, tint = Colors.GrayDark)
        Spacer(Modifier.width(7.dp))
        Texts.General(text, fontSize = 14.sp, color = Colors.GrayDark)
    }
}

