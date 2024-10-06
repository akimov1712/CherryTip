package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.apply
import cherrytip.composeapp.generated.resources.cancel
import cherrytip.composeapp.generated.resources.ok
import cherrytip.composeapp.generated.resources.select_date
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.utills.toLocalDate

@Composable
fun DialogWrapper(
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(color = Colors.White, RoundedCornerShape(24.dp))
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit,
    onCancel: (LocalDate) -> Unit,
) {
    val localDate by derivedStateOf { state.selectedDateMillis?.let { GMTDate(it).toLocalDate() } }
    DatePickerDialog(
        colors = DatePickerDefaults.colors(Colors.White),
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    localDate?.let { onConfirm(it) }
                },
            ) {
                Texts.Button(
                    text = stringResource(Res.string.ok),
                    color = Colors.Purple,
                    fontSize = 16.sp
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    localDate?.let { onCancel(it) }
                }
            ) {
                Texts.Button(
                    text = stringResource(Res.string.cancel),
                    color = Colors.Purple,
                    fontSize = 16.sp
                )
            }
        },
    ) {
        DatePicker(
            modifier = Modifier.background(Colors.White, RoundedCornerShape(20.dp)),
            state = state,
            showModeToggle = false,
            title = null,
            headline = {
                Texts.Option(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
                    fontSize = 24.sp,
                    color = Colors.Black,
                    text = localDate?.let { "${it.month.name.take(3)} ${it.dayOfMonth}, ${it.year}" } ?: stringResource(Res.string.select_date)
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = Colors.White,
                selectedDayContentColor = Colors.White,
                selectedDayContainerColor = Colors.Purple,
                selectedYearContentColor = Colors.White,
                selectedYearContainerColor = Colors.Purple,
                todayDateBorderColor = Colors.Purple,
            )
        )
    }
}
