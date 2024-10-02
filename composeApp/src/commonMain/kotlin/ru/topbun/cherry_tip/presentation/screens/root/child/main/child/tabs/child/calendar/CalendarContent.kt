package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.calendar
import cherrytip.composeapp.generated.resources.carbs
import cherrytip.composeapp.generated.resources.eaten
import cherrytip.composeapp.generated.resources.fat
import cherrytip.composeapp.generated.resources.goal_goal
import cherrytip.composeapp.generated.resources.ic_add
import cherrytip.composeapp.generated.resources.ic_append
import cherrytip.composeapp.generated.resources.ic_calendar
import cherrytip.composeapp.generated.resources.ic_cutlery
import cherrytip.composeapp.generated.resources.ic_scale
import cherrytip.composeapp.generated.resources.protein
import cherrytip.composeapp.generated.resources.remaining
import dev.darkokoa.datetimewheelpicker.core.isAfter
import io.ktor.http.HttpHeaders.Date
import io.ktor.http.toHttpDate
import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.RecipeEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.NutrientsItem
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBars
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.getPeriodDate
import ru.topbun.cherry_tip.utills.now
import ru.topbun.cherry_tip.utills.toLocalDate

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header{}
        Spacer(Modifier.height(10.dp))
        CalendarSlider(getPeriodDate(GMTDate(0,0,0,2,Month.AUGUST, 2024)))
        Spacer(Modifier.height(16.dp))
        Information()
        Spacer(Modifier.height(16.dp))
        Ingestion()
    }
}

@Composable
private fun Ingestion() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val calendaries =  CalendarEnum.entries
        calendaries.forEachIndexed { index, calendar ->
            IngestionItem(calendar = calendar, value = "508 / 889 kcal")
            if (index != calendaries.lastIndex) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
        }
    }
}

@Composable
fun IngestionItem(calendar: CalendarEnum, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                progress = { 0.7f },
                color = Colors.Purple,
                trackColor = Colors.PurpleBackground,
                strokeCap = StrokeCap.Round,
                strokeWidth = 5.dp
            )
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(calendar.icon),
                contentDescription = null
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Texts.Option(
                text = stringResource(calendar.title),
                fontSize = 16.sp,
                color = Colors.Black
            )
            Texts.Light(
                text = value,
                fontSize = 14.sp
            )
        }
        IconButton(
            onClick = {}
        ){
            Icon(
                painter = painterResource(Res.drawable.ic_append),
                contentDescription = null,
                tint = Colors.Purple
            )
        }
    }
}

@Composable
private fun Information() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(20.dp))
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MainInformation()
        Nutrients()
    }
}

@Composable
private fun MainInformation() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconWithValue(
            icon = painterResource(Res.drawable.ic_cutlery),
            title = stringResource(Res.string.eaten),
            value = "1408"
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            ProgressBars.CropProgressIndicator(
                modifier = Modifier.size(140.dp),
                value = 0.5f,
                percentFillRound = 0.7f,
                strokeWidth = 12.dp
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.Title(
                    text = "1 556",
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    fontSize = 20.sp
                )
                Texts.Light(
                    modifier = Modifier.offset(y = -6.dp),
                    text = stringResource(Res.string.remaining)
                )
            }
        }
        IconWithValue(
            icon = painterResource(Res.drawable.ic_scale),
            title = stringResource(Res.string.goal_goal),
            value = "LW"
        )
    }
}

@Composable
fun Nutrients() {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        NutrientsItem(
            text = stringResource(Res.string.protein),
            value = "40 / 145 g",
            progress = 0.1f,
            progressColor = Colors.GreenLight
        )
        NutrientsItem(
            text = stringResource(Res.string.carbs),
            value = "200 / 361 g",
            progress = 0.55f,
            progressColor = Colors.Blue
        )
        NutrientsItem(
            text = stringResource(Res.string.fat),
            value = "54 / 96 g",
            progress = 0.25f,
            progressColor = Colors.Yellow
        )
    }
}

@Composable
private fun IconWithValue(
    icon: Painter,
    title: String,
    value: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon ,
            contentDescription = null,
            tint = Colors.DarkGray
        )
        Spacer(Modifier.height(5.dp))
        Texts.Option(
            text = value,
            fontSize = 16.sp,
            color = Colors.Black
        )
        Texts.Light(text = title)
    }
}

@Composable
fun CalendarSlider(dates: List<LocalDate>) {
    var selectedIndex by remember { mutableStateOf(dates.lastIndex) }
    LazyRow(
        state = rememberLazyListState(dates.size - 1),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Spacer(Modifier.width(20.dp)) }
        items(items = dates, key = {it.toEpochDays()}){
            val isSelected = it == dates[selectedIndex]
            val background = if(isSelected) Colors.Purple else Colors.PurpleBackground
            val colorText = if(isSelected) Colors.White else Colors.Gray
                Column(
                modifier = Modifier
                    .size(65.dp, 80.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(background)
                    .clickable { selectedIndex = dates.indexOf(it) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.Option(
                    it.dayOfMonth.toString(),
                    fontSize = 16.sp,
                    color = colorText
                )
                Texts.Light(
                    it.month.name.take(3).lowercase().capitalize(),
                    fontSize = 14.sp,
                    color = colorText
                )
            }
        }
        item { Spacer(Modifier.width(20.dp)) }
    }
}

@Composable
private fun Header(onClickChoiceDate: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Texts.Title(stringResource(Res.string.calendar))
        IconButton(
            onClick = onClickChoiceDate
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_calendar),
                contentDescription = null,
                tint = Colors.Purple
            )
        }
    }
}