package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.after
import cherrytip.composeapp.generated.resources.challenge_completed
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_bulb
import cherrytip.composeapp.generated.resources.ic_play
import cherrytip.composeapp.generated.resources.ic_stop
import cherrytip.composeapp.generated.resources.many_days
import cherrytip.composeapp.generated.resources.preparing_challenge
import cherrytip.composeapp.generated.resources.start
import cherrytip.composeapp.generated.resources.start_again
import cherrytip.composeapp.generated.resources.stop
import cherrytip.composeapp.generated.resources.time_end
import cherrytip.composeapp.generated.resources.time_start
import io.ktor.util.date.GMTDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.Const
import ru.topbun.cherry_tip.utills.getResourceDeclinationEndingDays
import ru.topbun.cherry_tip.utills.getResourceEndingDays

@Composable
fun ChallengeDetailScreen(
    component: ChallengeDetailComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        val state by component.state.collectAsState()
        var title by rememberSaveable { mutableStateOf<String?>(null) }
        BackWithTitle(title) { component.clickBack() }
        Spacer(Modifier.height(30.dp))
        when (val screenState = state.challengeState) {
            is ChallengeDetailStore.State.ChallengeState.Error -> {
                ErrorContent(
                    modifier = Modifier.weight(1f),
                    text = screenState.text
                ){ component.load() }
            }

            ChallengeDetailStore.State.ChallengeState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Colors.Purple)
                }
            }

            is ChallengeDetailStore.State.ChallengeState.Success -> {
                title = screenState.challenge.title
                InfoDateChallenge(screenState.challenge)
                Spacer(Modifier.height(10.dp))
                if (screenState.challenge.userChallenge?.status == ChallengeStatus.Finished){
                    Texts.General(
                        text = stringResource(Res.string.challenge_completed),
                        color = Colors.GreenDark,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(Modifier.height(10.dp))
                ChallengeButton(component, screenState.challenge)
                Spacer(Modifier.height(20.dp))
                Preparing(screenState.challenge)
            }

            else -> {}
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
    val state by component.state.collectAsState()
    val isLoading = state.changeStatusState == ChallengeDetailStore.State.ChangeStatusState.Loading
    val color = challenge.userChallenge?.let {
        when (it.status) {
            ChallengeStatus.Active -> Colors.Red
            ChallengeStatus.Finished -> Colors.Green
            ChallengeStatus.Canceled -> Colors.Green
            else -> {
                throw RuntimeException("Challenge cannot have this status")
            }
        }
    } ?: Colors.Green
    val text = stringResource(
        challenge.userChallenge?.let {
            when (it.status) {
                ChallengeStatus.Active -> Res.string.stop
                ChallengeStatus.Finished -> Res.string.start_again
                ChallengeStatus.Canceled -> Res.string.start
                else -> {
                    throw RuntimeException("Challenge cannot have this status")
                }
            }
        } ?: Res.string.start
    )
    val iconRes = challenge.userChallenge?.let {
        when (it.status) {
            ChallengeStatus.Active -> Res.drawable.ic_stop
            ChallengeStatus.Finished -> Res.drawable.ic_play
            ChallengeStatus.Canceled -> Res.drawable.ic_play
            else -> {
                throw RuntimeException("Challenge cannot have this status")
            }
        }
    } ?: Res.drawable.ic_play
    Buttons.Button(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        onClick = { component.changeChallengeStatus() },
        shape = RoundedCornerShape(13.dp),
        enabled = !isLoading,
        containerColor = color,
    ) {
        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Colors.White,
                strokeWidth = 2.dp
            )
        } else{
            Row(verticalAlignment = Alignment.CenterVertically) {
                Texts.Button(
                    text = text,
                    color = Colors.White
                )
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
        TextWithDate(stringResource(Res.string.time_start), date.startDate)
        Row {
            Box(Modifier.weight(1f).height(1.dp).padding(end = 10.dp).background(Colors.GrayLight))
            Spacer(Modifier.weight(1f))
        }
        TextWithDate("${stringResource(Res.string.time_end)} (${stringResource(Res.string.after)} ${challenge.durationDays} ${stringResource(challenge.durationDays.getResourceDeclinationEndingDays())})", date.endDate)
    }
}

@Composable
private fun TextWithDate(text: String, date: String) {
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
private fun BackWithTitle(title: String?, clickBack: () -> Unit) {
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
        Texts.Title(text = title ?: stringResource(Res.string.challenges))
    }
}

private fun formatDate(challenge: ChallengeEntity): ChallengeDetailDate {
    val nowDate = run {
        val startDate = GMTDate()
        val endDate = calculateEndDate(startDate, challenge.durationDays)
        ChallengeDetailDate(
            startDate = formatDateTime(startDate),
            endDate = formatDateTime(endDate)
        )
    }
    return challenge.userChallenge?.let { userChallenge ->
        if(userChallenge.status == ChallengeStatus.Canceled) return nowDate
        val startDate = userChallenge.startDate
        val endDate = calculateEndDate(startDate, challenge.durationDays)

        ChallengeDetailDate(
            startDate = formatDateTime(startDate),
            endDate = formatDateTime(endDate)
        )
    } ?: nowDate
}

private fun calculateEndDate(startDate: GMTDate, durationDays: Int): GMTDate {
    val endDateMillis = startDate.timestamp + Const.SECOND_IN_DAY * durationDays
    return GMTDate(endDateMillis)
}

private fun formatDateTime(date: GMTDate): String {
    val month = date.month.name.lowercase().capitalize().take(3)
    val day = date.dayOfMonth
    val hours = date.hours.toString().padStart(2, '0')
    val minutes = date.minutes.toString().padStart(2, '0')
    val time = "${hours}:${minutes}"

    return "$month.$day ; $time"
}

private data class ChallengeDetailDate(
    val startDate: String,
    val endDate: String
)