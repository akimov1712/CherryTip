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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import cherrytip.composeapp.generated.resources.preparing_challenge
import io.ktor.util.date.GMTDate
import io.ktor.util.date.plus
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.Const

@Composable
fun ChallengeDetailScreen(
    component: ChallengeDetailComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val snackBarHost = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        snackbarHost = {
            SnackbarHost(snackBarHost)
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(20.dp)
        ) {
            val state by component.state.collectAsState()
            BackWithTitle{ component.clickBack() }
            Spacer(Modifier.height(30.dp))
            when(val screenState = state.challengeState){
                is ChallengeDetailStore.State.ChallengeState.Error -> {
                    rememberCoroutineScope().launch {
                        snackBarHost.showSnackbar(screenState.text)
                    }
                }
                ChallengeDetailStore.State.ChallengeState.Loading -> {
                    Box(Modifier.fillMaxSize()){
                        CircularProgressIndicator(color = Colors.Purple)
                    }
                }
                is ChallengeDetailStore.State.ChallengeState.Success -> {
                    InfoDateChallenge(screenState.challenge)
                    Spacer(Modifier.height(20.dp))
                    ChallengeButton(component, screenState.challenge)
                    Spacer(Modifier.height(20.dp))
                    Preparing(screenState.challenge)
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun Preparing(challenge: ChallengeEntity) {
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
                text = stringResource(Res.string.preparing_challenge),
                color = Colors.Black
            )
        }
        Spacer(Modifier.height(20.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            challenge.tips.forEach { TipItem(it) }
        }
    }
}

@Composable
private fun TipItem(text: String) {
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
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ChallengeButton(component: ChallengeDetailComponent, challenge: ChallengeEntity) {
    val isFinished = challenge.userChallenge?.let { it.status == ChallengeStatus.Finished } ?: false
    val color = challenge.userChallenge?.let {
        when(it.status){
            ChallengeStatus.Active -> Colors.Red
            ChallengeStatus.Finished -> Colors.GrayDark
            ChallengeStatus.Canceled -> Colors.Green
            else -> { throw RuntimeException("Challenge cannot have this status") }
        }
    } ?: Colors.Green
    val text = challenge.userChallenge?.let {
        when(it.status){
            ChallengeStatus.Active -> "Stop"
            ChallengeStatus.Finished -> "Finished"
            ChallengeStatus.Canceled -> "Start"
            else -> { throw RuntimeException("Challenge cannot have this status") }
        }
    } ?: "Start"
    val iconRes = challenge.userChallenge?.let {
        when(it.status){
            ChallengeStatus.Active -> Res.drawable.ic_stop
            ChallengeStatus.Finished -> null
            ChallengeStatus.Canceled ->  Res.drawable.ic_play
            else -> { throw RuntimeException("Challenge cannot have this status") }
        }
    } ?: Res.drawable.ic_play
    Buttons.Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {  },
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        enabled = !isFinished,
        contentPadding = PaddingValues(vertical = 18.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Texts.Button(
                text = text,
                color = Colors.White
            )
            if (!isFinished){
                Spacer(Modifier.width(7.dp))
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(iconRes),
                    tint = Colors.White,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun InfoDateChallenge(challenge: ChallengeEntity) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val date = formatDate(challenge)
        TextWithDate("Start", date.startDate)
        Row {
            Box(Modifier.weight(1f).height(1.dp).padding(end = 10.dp).background(Colors.GrayLight))
            Spacer(Modifier.weight(1f))
        }
        TextWithDate("End (after ${challenge.durationDays} days)", date.endDate)
    }
}

@Composable
private fun TextWithDate(text: String, date : String) {
    Row(modifier = Modifier.height(55.dp), verticalAlignment = Alignment.CenterVertically) {
        Texts.Option(
            text = text,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Colors.Gray,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.width(20.dp))
        Texts.Option(
            modifier = Modifier.weight(1f),
            text = date,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            color = Colors.Black
        )
        Spacer(Modifier.width(18.dp))
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

private fun formatDate(challenge: ChallengeEntity): ChallengeDetailDate {
    return challenge.userChallenge?.let { userChallenge ->
        val startDate = userChallenge.startDate
        val endDate = calculateEndDate(startDate, challenge.durationDays)

        ChallengeDetailDate(
            startDate = formatDateTime(startDate),
            endDate = formatDateTime(endDate)
        )
    } ?: ChallengeDetailDate("", "")
}

private fun calculateEndDate(startDate: GMTDate, durationDays: Int): GMTDate {
    val endDateMillis = startDate.timestamp + Const.SECOND_IN_DAY * durationDays
    return GMTDate(endDateMillis)
}

private fun formatDateTime(date: GMTDate): String {
    val month = date.month.name
    val day = date.dayOfMonth
    val time = "${date.hours}:${date.minutes.toString().padStart(2, '0')}"

    return "$month.$day ; $time"
}

private data class ChallengeDetailDate(
    val startDate: String,
    val endDate: String
)