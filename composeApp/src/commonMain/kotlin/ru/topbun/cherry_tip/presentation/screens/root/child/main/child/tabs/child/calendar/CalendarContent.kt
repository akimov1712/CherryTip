package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.calendar
import cherrytip.composeapp.generated.resources.cancel
import cherrytip.composeapp.generated.resources.carbs
import cherrytip.composeapp.generated.resources.eaten
import cherrytip.composeapp.generated.resources.fat
import cherrytip.composeapp.generated.resources.goal_goal
import cherrytip.composeapp.generated.resources.ic_append
import cherrytip.composeapp.generated.resources.ic_calendar
import cherrytip.composeapp.generated.resources.ic_cutlery
import cherrytip.composeapp.generated.resources.ic_scale
import cherrytip.composeapp.generated.resources.ok
import cherrytip.composeapp.generated.resources.protein
import cherrytip.composeapp.generated.resources.remaining
import cherrytip.composeapp.generated.resources.retry
import cherrytip.composeapp.generated.resources.select_date
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarEntity
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponent
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.DialogDatePicker
import ru.topbun.cherry_tip.presentation.ui.components.ErrorContent
import ru.topbun.cherry_tip.presentation.ui.components.NutrientsItem
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBars
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.getPeriodDate
import ru.topbun.cherry_tip.utills.now
import ru.topbun.cherry_tip.utills.toLocalDate
import ru.topbun.cherry_tip.utills.toStringOrBlank
import kotlin.math.roundToInt

@Composable
fun CalendarScreen(
    component: CalendarComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val state by component.state.collectAsState()
    var openChoiceDateModal by remember { mutableStateOf(false) }
    Column(
        modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header{ openChoiceDateModal = true }
        Spacer(Modifier.height(10.dp))
        CalendarSlider(component)
        Spacer(Modifier.height(16.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            when(val screenState = state.calendarState){
                is CalendarStore.State.CalendarState.Error -> {
                    ErrorContent(modifier = Modifier.padding(horizontal = 20.dp), text = screenState.msg){
                        component.loadCalendar()
                    }
                }
                CalendarStore.State.CalendarState.Loading -> CircularProgressIndicator(color = Colors.Purple)
                CalendarStore.State.CalendarState.Result -> {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Information(component)
                        Spacer(Modifier.height(16.dp))
                        Ingestion(component)
                    }
                }
                else -> {}
            }
        }
    }

    if (state.listDays.isNotEmpty() && openChoiceDateModal) {
        DatePicker(component){ openChoiceDateModal = false }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(component: CalendarComponent, onDismiss: () -> Unit) {
    val state by component.state.collectAsState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
        yearRange = IntRange(state.listDays.first().year,state.listDays.last().year)
    )
    DialogDatePicker(
        datePickerState,
        onDismissRequest = { onDismiss() },
        onConfirm = { onDismiss(); component.changeDay(it) },
        onCancel = { onDismiss() }
    )
}

@Composable
private fun Ingestion(component: CalendarComponent) {
    val state by component.state.collectAsState()
    val calendarState by state.getCalendar()
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(20.dp)),
    ) {
        val calendaries =  CalendarObjects.entries
        calendaries.forEachIndexed { index, calendar ->
            val value = when(calendar.type){
                CalendarType.Breakfast -> getValueIngestion(calendarState, CalendarType.Breakfast, calendarState.breakfast)
                CalendarType.Lunch -> getValueIngestion(calendarState, CalendarType.Lunch, calendarState.lunch)
                CalendarType.Dinner -> getValueIngestion(calendarState, CalendarType.Dinner, calendarState.dinner)
                CalendarType.Snack -> getValueIngestion(calendarState, CalendarType.Snack, calendarState.snack)
            }
            val progress = when(calendar.type){
                CalendarType.Breakfast -> getProgressIngestion(calendarState, CalendarType.Breakfast, calendarState.breakfast)
                CalendarType.Lunch -> getProgressIngestion(calendarState, CalendarType.Lunch, calendarState.lunch)
                CalendarType.Dinner -> getProgressIngestion(calendarState, CalendarType.Dinner, calendarState.dinner)
                CalendarType.Snack -> getProgressIngestion(calendarState, CalendarType.Snack, calendarState.snack)
            }
            IngestionItem(
                calendar = calendar,
                value = value,
                progress = progress,
                onClickIngest = { component.openDetailIngest(date = state.selectedDay, type = it) }
            ){
                component.openAppendMeal(date = state.selectedDay, type = it)
            }
            if (index != calendaries.lastIndex) Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
        }
    }
}

private fun getValueIngestion(calendar: CalendarEntity, type: CalendarType, kcalIngestion: Int): String{
    val eatenKcalories = calendar.recipes.filter { it.category == type }.map { it.recipes.map { it.calories } }.flatten().sum()
    return "$eatenKcalories/$kcalIngestion kcal"
}

private fun getProgressIngestion(calendar: CalendarEntity, type: CalendarType, kcalIngestion: Int): Float{
    val eatenKcalories = calendar.recipes.filter { it.category == type }.map { it.recipes.map { it.calories } }.flatten().sum()
    return eatenKcalories.toFloat()/kcalIngestion
}

@Composable
fun IngestionItem(calendar: CalendarObjects, value: String, progress: Float,
                  onClickIngest:(CalendarType) -> Unit, onClickButton: (CalendarType) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClickIngest(calendar.type) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                progress = { progress },
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
            onClick = { onClickButton(calendar.type) }
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
private fun Information(component: CalendarComponent) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(20.dp))
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MainInformation(component)
        Nutrients(component)
    }
}

@Composable
private fun MainInformation(component: CalendarComponent) {
    val state by component.state.collectAsState()
    val calendar by state.getCalendar()
    val eatenCalories by derivedStateOf { calendar.recipes.map { it.recipes.map { it.calories } }.flatten().sum()  }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconWithValue(
            icon = painterResource(Res.drawable.ic_cutlery),
            title = stringResource(Res.string.eaten),
            value = eatenCalories.toString()
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            ProgressBars.CropProgressIndicator(
                modifier = Modifier.size(140.dp),
                value = eatenCalories / calendar.needCalories.toFloat(),
                percentFillRound = 0.7f,
                strokeWidth = 12.dp
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Texts.Title(
                    text = (calendar.needCalories - eatenCalories).coerceAtLeast(0).toString(),
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
            value = calendar.goal.toString()
        )
    }
}

@Composable
fun Nutrients(component: CalendarComponent) {
    val state by component.state.collectAsState()
    val calendar by state.getCalendar()
    val eatenProtein = calendar.recipes.map { it.recipes.map { it.protein.toFloat().roundToInt() } }.flatten().sum()
    val eatenCarbs = calendar.recipes.map { it.recipes.map { it.carbs.toFloat().roundToInt() } }.flatten().sum()
    val eatenFat = calendar.recipes.map { it.recipes.map { it.fat.toFloat().roundToInt() } }.flatten().sum()
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        NutrientsItem(
            text = stringResource(Res.string.protein),
            value = "$eatenProtein/${calendar.protein} g",
            progress = eatenProtein.toFloat() / calendar.protein,
            progressColor = Colors.GreenLight
        )
        NutrientsItem(
            text = stringResource(Res.string.carbs),
            value = "$eatenCarbs/${calendar.carbs} g",
            progress = eatenCarbs.toFloat() / calendar.carbs,
            progressColor = Colors.Blue
        )
        NutrientsItem(
            text = stringResource(Res.string.fat),
            value = "$eatenFat/${calendar.fat} g",
            progress = eatenFat.toFloat() / calendar.fat,
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
fun CalendarSlider(component: CalendarComponent) {
    val state by component.state.collectAsState()
    LazyRow(
        state = rememberLazyListState(if (state.listDays.isNotEmpty()) state.listDays.lastIndex else 0),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { Spacer(Modifier.width(20.dp)) }
        items(items = state.listDays, key = {it.toEpochDays()}){
            val isSelected = it == state.selectedDay
            val background = if(isSelected) Colors.Purple else Colors.PurpleBackground
            val colorText = if(isSelected) Colors.White else Colors.Gray
                Column(
                modifier = Modifier
                    .size(65.dp, 80.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(background)
                    .clickable { component.changeDay(it) },
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

private fun CalendarStore.State.getCalendar() = derivedStateOf { this.calendar ?: throw RuntimeException("Calendar is not loaded") }