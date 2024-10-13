package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.active
import cherrytip.composeapp.generated.resources.all
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.challenges_is_empty
import cherrytip.composeapp.generated.resources.easy
import cherrytip.composeapp.generated.resources.finished
import cherrytip.composeapp.generated.resources.hard
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_lightning
import cherrytip.composeapp.generated.resources.more
import cherrytip.composeapp.generated.resources.normal
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.Difficulty.Easy
import ru.topbun.cherry_tip.domain.entity.Difficulty.Hard
import ru.topbun.cherry_tip.domain.entity.Difficulty.Normal
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.NotFoundContent
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.getResourceEndingDays

@Composable
fun ChallengeScreen(
    component: ChallengeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        val state by component.state.collectAsState()
        BackWithTitle(stringResource(Res.string.challenges)) { component.clickBack() }
        Spacer(Modifier.height(30.dp))
        CustomTabRow(
            items = state.items.map {
                stringResource(
                    when(it){
                        ChallengeStatus.Active -> Res.string.active
                        ChallengeStatus.Finished -> Res.string.finished
                        else -> Res.string.all
                    }
                )
            },
            selectedIndex = state.selectedIndex
        ) {
            component.choiceChallengeStatus(it)
        }
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            when (val screenState = state.challengeStateStatus) {
                is ChallengeStore.State.ChallengeState.Error -> {
                    ErrorContent(
                        modifier = Modifier.align(Alignment.Center),
                        text = screenState.text
                    ){ component.loadChallenge(state.items[state.selectedIndex]) }
                }

                ChallengeStore.State.ChallengeState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center), color = Colors.Purple)
                }

                is ChallengeStore.State.ChallengeState.Result -> {
                    if (screenState.challenges.isEmpty()) {
                        NotFoundContent(Modifier.padding(horizontal = 20.dp).align(Alignment.Center))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items = screenState.challenges, key = { it.id }) {
                                ChallengeItem(challenge = it) { component.openChallengeDetail(it.id) }
                            }
                            item {
                                Spacer(Modifier.height(16.dp))
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun ChallengeItem(challenge: ChallengeEntity, onClickMore: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = challenge.color)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            InfoChallenge(
                challenge = challenge,
                modifier = Modifier.weight(1f),
                onClickMore = onClickMore
            )
            var isLoading by rememberSaveable { mutableStateOf(true) }
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Colors.White)
                }
            }
            AsyncImage(
                model = challenge.image,
                onState = { isLoading = it is AsyncImagePainter.State.Loading },
                alignment = Alignment.BottomEnd,
                modifier = Modifier.weight(1f).align(Alignment.Bottom).scale(1.15f),
                contentScale = ContentScale.FillWidth,
                contentDescription = challenge.title
            )
        }
    }
}

@Composable
private fun InfoChallenge(challenge: ChallengeEntity, modifier: Modifier, onClickMore: () -> Unit) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Texts.Option(
            text = challenge.title,
            color = Colors.Black,
            textAlign = TextAlign.Start
        )
        IconWithText(
            painter = painterResource(Res.drawable.ic_clock),
            text = "${challenge.durationDays} ${stringResource(challenge.durationDays.getResourceEndingDays())}"
        )
        IconWithText(
            painter = painterResource(Res.drawable.ic_lightning),
            text = stringResource(
                when(challenge.difficulty){
                    Easy -> Res.string.easy
                    Normal -> Res.string.normal
                    Hard -> Res.string.hard
                }
            )
        )
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
        Icon(painter = painter, contentDescription = null, tint = Colors.DarkGray)
        Spacer(Modifier.width(7.dp))
        Texts.General(text, fontSize = 14.sp, color = Colors.DarkGray)
    }
}

