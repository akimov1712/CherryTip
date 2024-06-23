package ru.topbun.cherry_tip.presentation.screens.auth.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.step
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.NameFragmentContent
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBar
import ru.topbun.cherry_tip.presentation.ui.components.Text

@Composable
fun SurveyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
    }
}

@Composable
private fun Header() {
    var progress by rememberSaveable {
        mutableStateOf(0.1f)
    }
    ProgressBar.Default(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(CircleShape),
        progressColor = Colors.Purple,
        backgroundColor = Colors.PurpleBackground,
        progress = progress,
        shapeProgress = CircleShape
    )
    Spacer(Modifier.height(40.dp))
    Text.Option(stringResource(Res.string.step) + " 1 / 9", fontSize = 16.sp)
    Spacer(Modifier.height(40.dp))
    NameFragmentContent{

    }
}

