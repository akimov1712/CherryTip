package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.error_challenge
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_lightning
import cherrytip.composeapp.generated.resources.more
import cherrytip.composeapp.generated.resources.see_all
import coil3.compose.AsyncImage
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun Challenge(component: HomeComponent) {
    val state by component.state.collectAsState()
    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Texts.Title(stringResource(Res.string.challenges), textAlign = TextAlign.Start)
        Spacer(Modifier.weight(1f))
        Texts.Option(
            stringResource(Res.string.see_all),
            fontSize = 16.sp,
            modifier = Modifier.clickable (interactionSource = MutableInteractionSource(),
                indication = null, onClick = { component.openChallenge() })
        )
    }
    Spacer(Modifier.height(16.dp))
    Box(Modifier.fillMaxWidth().defaultMinSize(minHeight = 150.dp), contentAlignment = Alignment.Center){
        when(val screenState = state.challengeStateStatus){
            is HomeStore.State.ChallengeStateStatus.Error -> Texts.Error(text = screenState.text)
            HomeStore.State.ChallengeStateStatus.Loading -> CircularProgressIndicator(color = Colors.Purple)
            is HomeStore.State.ChallengeStateStatus.Result -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(items = screenState.result.challengeStatus, key = {it.title}){
                        ChallengeItem(it){component.openChallengeDetail()}
                    }
                }
            }
            else -> {}
        }
    }


}

@Composable
private fun ChallengeItem(challenge: ChallengeEntity, onClickMore: () -> Unit) {
    Card(
        modifier = Modifier.width(310.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = challenge.color)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            InfoChallenge(challenge = challenge,onClickMore = onClickMore)
            AsyncImage(
                modifier = Modifier.fillMaxWidth().offset(y = 30.dp, x = 10.dp).scale(1.2f),
                model = challenge.image,
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun InfoChallenge(challenge: ChallengeEntity, modifier: Modifier = Modifier, onClickMore: () -> Unit) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Texts.Option(challenge.title, color = Colors.Black)
        IconWithText(painter = painterResource(Res.drawable.ic_clock), text = "${challenge.durationDays} days")
        IconWithText(painter = painterResource(Res.drawable.ic_lightning), text = challenge.difficulty.name)
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

