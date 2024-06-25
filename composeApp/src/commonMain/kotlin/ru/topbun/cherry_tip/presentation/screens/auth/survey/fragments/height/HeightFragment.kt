package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.height

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.height_warning
import cherrytip.composeapp.generated.resources.ic_info
import cherrytip.composeapp.generated.resources.what_your_height
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.FragmentsComponents
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun HeightFragmentContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_height)
    ){
        var height by rememberSaveable{ mutableStateOf(100) }
        Spacer(Modifier.weight(1f))
        FragmentsComponents.NumberSlidePicker(
            modifier = Modifier.padding(horizontal = 20.dp),
            minValue = 100,
            maxValue = 250,
            unit = "cm"
        ){
            height = it
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.PurpleBackground, RoundedCornerShape(16.dp))
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_info),
                contentDescription = null,
                tint = Colors.GrayDark
            )
            Spacer(Modifier.width(10.dp))
            Texts.Option(
                text = stringResource(Res.string.height_warning),
                color = Colors.GrayDark,
                textAlign = TextAlign.Start
            )
        }
        Spacer(Modifier.weight(1f))
        FragmentsComponents.ButtonsNavigation(
            onClickBack = onClickBack,
            onClickContinue = onClickContinue
        )
        Spacer(Modifier.height(20.dp))
    }
}