package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.get_started
import cherrytip.composeapp.generated.resources.next
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.presentation.ui.utills.getScreenSizeInfo
import ru.topbun.cherry_tip.presentation.ui.utills.setColorStatusBar


@Composable
fun ReminderScreen(
    component: ReminderComponent,
    modifier: Modifier = Modifier.background(Colors.White)
) {
    setColorStatusBar(Colors.Transparent, false)
    val state by component.state.collectAsState()
    Box(modifier) {
        ReminderImage(state.screens[state.indexSelected])
        ReminderCard(component, state)
    }
}

@Composable
private fun ReminderCard(component: ReminderComponent, state: ReminderStore.State) {
    Column {
        Spacer(Modifier.weight(3f))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            colors = CardDefaults.cardColors(containerColor = Colors.White),

            ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val isLastScreen = state.screens.last() == state.screens[state.indexSelected]
                Texts.Title(stringResource(state.screens[state.indexSelected].titleRes), fontSize = 30.sp)
                Spacer(Modifier.height(20.dp))
                Texts.General(
                    modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                    text = stringResource(state.screens[state.indexSelected].descrRes)
                )
                Spacer(Modifier.height(10.dp))
                IndexState(state.screens.size, state.indexSelected){component.setIndexSelected(it)}
                Spacer(Modifier.height(20.dp))
                Buttons.Gray(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = {
                        if (!isLastScreen) component.setIndexSelected(state.indexSelected + 1)
                        else component.finishedAuth()
                    }
                ) {
                    val text = stringResource(if(isLastScreen) Res.string.get_started else Res.string.next)
                    Texts.Button(text, fontSize = 16.sp, color = Colors.Purple)
                }
            }
        }
    }
}

@Composable
private fun IndexState(count: Int, indexSelected: Int, onChangeIndex: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        repeat(count){
            Card(
                onClick = {onChangeIndex(it)},
                modifier = Modifier
                    .size(10.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = if (it == indexSelected) Colors.Purple else Colors.PurpleBackground),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp)
            ){}
        }
    }
}

@Composable
private fun ReminderImage(screen: ReminderScreens) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.Purple)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            painter = painterResource(screen.imageRes),
            contentDescription = null
        )
        val size = getScreenSizeInfo()
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        listOf(Colors.Transparent, Colors.Purple.copy(0.6f)),
                        center = Offset(x = size.wDP.value / 1.5f, y = size.hDP.value / 2)
                    )
                )
        )
    }
}