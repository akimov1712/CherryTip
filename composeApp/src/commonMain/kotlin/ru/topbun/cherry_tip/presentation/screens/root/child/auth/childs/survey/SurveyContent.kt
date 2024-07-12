package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.age.AgeFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.GenderFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.height.HeightFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.name.NameFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.targetWeight.TargetWeightFragmentContent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.weight.WeightFragmentContent
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.ProgressBars
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.Log

@Composable
fun SurveyScreen(
    component: SurveyComponent,
    modifier: Modifier = Modifier
) {
    val snackBarHost = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier.statusBarsPadding(),
        snackbarHost = {
            SnackbarHost(snackBarHost)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state by component.state.collectAsState()
            LaunchedEffect(state.surveyState) {
                when(val screenState = state.surveyState) {
                    is SurveyStore.State.SurveyState.Error -> snackBarHost.showSnackbar(screenState.error)
                    else -> {}
                }
            }
            Progress((state.selectedIndex + 1f)/ state.fragments.size)
            Spacer(Modifier.height(40.dp))
            Texts.Option(
                stringResource(Res.string.step) + " ${state.selectedIndex + 1} / ${state.fragments.size}",
                fontSize = 16.sp
            )
            Spacer(Modifier.height(40.dp))

            when (state.selectedFragment) {
                SurveyFragments.NAME -> NameFragmentContent(name = state.name) {
                    component.changeName(it)
                    component.nextFragment()
                }
                SurveyFragments.GOAL -> GoalFragmentContent(
                    onClickBack = { component.previousFragment() },
                    goal = state.goalType
                ) {
                    component.changeGoal(it)
                    component.nextFragment()
                }
                SurveyFragments.GENDER -> GenderFragmentContent(
                    onClickBack = { component.previousFragment() },
                    gender = state.gender
                ) {
                    component.changeGender(it)
                    component.nextFragment()
                }
                SurveyFragments.AGE -> AgeFragmentContent(
                    onClickBack = { component.previousFragment() },
                    age = state.age
                ) {
                    component.changeAge(it)
                    component.nextFragment()
                }
                SurveyFragments.HEIGHT -> HeightFragmentContent(
                    onClickBack = { component.previousFragment() },
                    height = state.height
                ) {
                    component.changeHeight(it)
                    component.nextFragment()
                }
                SurveyFragments.WEIGHT -> WeightFragmentContent(
                    onClickBack = { component.previousFragment() },
                    weight = state.weight
                ) {
                    component.changeWeight(it)
                    component.nextFragment()
                }
                SurveyFragments.TARGET_WEIGHT -> TargetWeightFragmentContent(
                    onClickBack = { component.previousFragment() },
                    targetWeight = state.targetWeight
                ) {
                    component.changeTargetWeight(it)
                    component.nextFragment()
                }
                SurveyFragments.ACTIVE -> ActiveFragmentContent(
                    onClickBack = { component.previousFragment() },
                    active = state.active
                ) {
                    component.changeActive(it)
                    component.sendSurvey()
                }

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


