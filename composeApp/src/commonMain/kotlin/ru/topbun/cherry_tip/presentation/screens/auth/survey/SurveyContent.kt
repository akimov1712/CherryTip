package ru.topbun.cherry_tip.presentation.screens.auth.survey

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.step
import io.ktor.util.date.GMTDate
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.active.ActiveFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.age.AgeFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.gender.GenderFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal.Goal
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.goal.GoalFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.height.HeightFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.name.NameFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.targetWeight.TargetWeightFragmentContent
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.weight.WeightFragmentContent
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBars
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun SurveyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fragments = SurveyFragments.entries
        var selectedFragment by remember { mutableStateOf(SurveyFragments.NAME) }
        val selectedIndex by remember { derivedStateOf { fragments.indexOf(selectedFragment) } }
        val progress by remember { derivedStateOf { (selectedIndex + 1f) / fragments.size } }
        val nextFragment = { selectedFragment = fragments[selectedIndex + 1] }
        val previousFragment = { selectedFragment = fragments[selectedIndex - 1] }

        Progress(progress)
        Spacer(Modifier.height(40.dp))
        Texts.Option(
            stringResource(Res.string.step) + " ${selectedIndex + 1} / ${fragments.size}",
            fontSize = 16.sp
        )
        Spacer(Modifier.height(40.dp))

        var name by rememberSaveable { mutableStateOf("") }
        var goal by remember { mutableStateOf(Goal.Lose) }
        var gender by remember { mutableStateOf(Gender.Female) }
        var date by remember { mutableStateOf(GMTDate()) }
        var height by rememberSaveable { mutableStateOf(100) }
        var weight by rememberSaveable { mutableStateOf(20) }
        var targetWeight by rememberSaveable { mutableStateOf(20) }
        var active by remember { mutableStateOf(ActiveType.MEDIUM) }

        when (selectedFragment) {
            SurveyFragments.NAME -> NameFragmentContent {
                name = it
                nextFragment()
            }
            SurveyFragments.GOAL -> GoalFragmentContent(onClickBack = previousFragment) {
                goal = it
                nextFragment()
            }
            SurveyFragments.GENDER -> GenderFragmentContent(onClickBack = previousFragment) {
                gender = it
                nextFragment()
            }
            SurveyFragments.AGE -> AgeFragmentContent(onClickBack = previousFragment) {
                date = it
                nextFragment()
            }
            SurveyFragments.HEIGHT -> HeightFragmentContent(onClickBack = previousFragment) {
                height = it
                nextFragment()
            }
            SurveyFragments.WEIGHT -> WeightFragmentContent(onClickBack = previousFragment) {
                weight = it
                nextFragment()
            }
            SurveyFragments.TARGET_WEIGHT -> TargetWeightFragmentContent(onClickBack = previousFragment) {
                targetWeight = it
                nextFragment()
            }
            SurveyFragments.ACTIVE -> ActiveFragmentContent(onClickBack = previousFragment) {
                active = it
            }

        }
    }
}

@Composable
private fun Progress(progress: Float) {
    ProgressBars.Default(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(CircleShape),
        progressColor = Colors.Purple,
        backgroundColor = Colors.PurpleBackground,
        progress = progress,
        shapeProgress = CircleShape
    )
}


