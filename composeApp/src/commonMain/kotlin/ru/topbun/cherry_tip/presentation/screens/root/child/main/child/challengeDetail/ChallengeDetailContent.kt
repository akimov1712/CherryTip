package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_bulb
import cherrytip.composeapp.generated.resources.ic_calendar
import cherrytip.composeapp.generated.resources.ic_play
import cherrytip.composeapp.generated.resources.ic_stop
import kotlinx.coroutines.NonCancellable.isActive
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun ChallengeDetailScreen(
    component: ChallengeDetailComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(20.dp)
    ) {
        BackWithTitle{ component.clickBack() }
        Spacer(Modifier.height(30.dp))
        InfoDateChallenge()
        Spacer(Modifier.height(20.dp))
        ChallengeButton()
        Spacer(Modifier.height(20.dp))
        Preparing()
    }
}

@Composable
private fun Preparing() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.PurpleBackground, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_bulb),
                tint = Colors.Black,
                contentDescription = null
            )
            Spacer(Modifier.width(5.dp))
            Texts.Option(
                text = "Preparing for the challenge",
                color = Colors.Black
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(6) {
                Row(
                    Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier.padding(top = 10.dp).size(4.dp)
                            .background(Colors.Gray, CircleShape)
                    )
                    Spacer(Modifier.width(10.dp))
                    Texts.General(
                        text = "Be prepared for possible disruptions",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
private fun ChallengeButton() {
    var isActive by rememberSaveable{ mutableStateOf(false) }
    Buttons.Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { isActive = !isActive },
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (isActive) Colors.Red else Colors.Green),
        contentPadding = PaddingValues(vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Texts.Button(
                text = if (isActive) "Stop" else "Start",
                color = Colors.White
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(if (isActive) Res.drawable.ic_stop else Res.drawable.ic_play),
                tint = Colors.White,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun InfoDateChallenge() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(modifier = Modifier.height(55.dp), verticalAlignment = Alignment.CenterVertically) {
            Texts.Option(
                text = "Start",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                color = Colors.Gray,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.width(20.dp))
            Buttons.Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                shape = RoundedCornerShape(13.dp),
                border = BorderStroke(1.dp, Colors.PurpleBackground),
                contentPadding = PaddingValues(vertical = 15.5.dp, horizontal = 18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.Transparent)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Texts.Option(text = "Oct.2 ; 17:32", fontSize = 16.sp)
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_calendar),
                        tint = Colors.Purple,
                        contentDescription = null
                    )
                }
            }
        }
        Row {
            Box(Modifier.weight(1f).height(1.dp).padding(end = 10.dp).background(Colors.GrayLight))
            Spacer(Modifier.weight(1f))
        }
        Row(modifier = Modifier.height(55.dp), verticalAlignment = Alignment.CenterVertically) {
            Texts.Option(
                text = "End",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                color = Colors.Gray,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.width(20.dp))
            Texts.Option(
                modifier = Modifier.weight(1f),
                text = "Oct.9 ; 17:32",
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                color = Colors.Black
            )
            Spacer(Modifier.width(18.dp))
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