package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.age

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.how_old_are_you
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.SurveyComponents
import ru.topbun.cherry_tip.utills.now
import ru.topbun.cherry_tip.utills.toGMTDate
import ru.topbun.cherry_tip.utills.toLocalDate

@Composable
fun AgeFragmentContent(
    modifier: Modifier = Modifier,
    age: GMTDate,
    onClickBack: () -> Unit,
    onClickContinue: (GMTDate) -> Unit
) {
    SurveyComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.how_old_are_you)
    ){
        var date by remember { mutableStateOf(age) }
        SurveyComponents.WheelDatePicker{ date = it.toGMTDate() }
        Spacer(Modifier.weight(1f))
        SurveyComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickNext = { onClickContinue(date) }
        )
        Spacer(Modifier.height(20.dp))
    }
}