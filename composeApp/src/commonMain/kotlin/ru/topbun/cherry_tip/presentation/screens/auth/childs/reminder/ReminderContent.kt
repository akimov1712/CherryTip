package ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.presentation.ui.utills.getScreenSizeInfo


@Composable
fun ReminderScreen(modifier: Modifier = Modifier) {
    Box(modifier) {
        val screens = listOf(ReminderScreens.Reminder1, ReminderScreens.Reminder2, ReminderScreens.Reminder3)
        var screenIndexSelected = rememberSaveable{ mutableStateOf(0) }
        ReminderImage(screens[screenIndexSelected.value])
        ReminderCard(screens, screenIndexSelected)
    }
}

@Composable
private fun ReminderCard(screens: List<ReminderScreens>, indexSelected: MutableState<Int>) {
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
                val isLastScreen = screens.last() == screens[indexSelected.value]
                Texts.Title(stringResource(screens[indexSelected.value].titleRes), fontSize = 30.sp)
                Spacer(Modifier.height(20.dp))
                Texts.General(stringResource(screens[indexSelected.value].descrRes))
                Spacer(Modifier.weight(1f))
                IndexState(screens.size, indexSelected.value){indexSelected.value = it}
                Spacer(Modifier.height(20.dp))
                Buttons.Gray(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = { if (!isLastScreen) indexSelected.value += 1 }
                ) {
                    val text = if(isLastScreen) "Get Started" else "Next"
                    Texts.Button(text, fontSize = 16.sp, color = Colors.Purple)
                }
                Spacer(Modifier.height(42.dp))
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