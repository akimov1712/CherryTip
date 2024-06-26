package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.age

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.how_old_are_you
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import dev.darkokoa.datetimewheelpicker.core.WheelPickerDefaults
import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.FragmentsComponents
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts
import ru.topbun.cherry_tip.utills.Log
import ru.topbun.cherry_tip.utills.now
import ru.topbun.cherry_tip.utills.toGMTDate

@Composable
fun AgeFragmentContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit
) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.how_old_are_you)
    ){
        var date by remember { mutableStateOf(GMTDate(getTimeMillis())) }
        WheelDatePicker(
            modifier = Modifier.fillMaxWidth(),
            minDate = LocalDate(1900, 1, 1),
            maxDate = LocalDate(LocalDate.now().year, 12 , 31),
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontFamily = Fonts.sfRegular
            ),
            size = DpSize(296.dp, 128.dp),
            textColor = Colors.Purple,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = Colors.PurpleBackground,
                border = BorderStroke(1.dp, Colors.Purple)
            )
        ) {
            date = it.toGMTDate()
        }
        Spacer(Modifier.weight(1f))
        FragmentsComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickNext = onClickContinue
        )
        Spacer(Modifier.height(20.dp))
    }
}