package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.calendar
import cherrytip.composeapp.generated.resources.ic_calendar
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
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