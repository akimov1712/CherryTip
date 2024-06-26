package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.topbun.cherry_tip.presentation.screens.auth.survey.SurveyScreen
import ru.topbun.cherry_tip.presentation.screens.splash.SplashScreen

@Composable
fun AppScreen() {
    MaterialTheme {
        SurveyScreen()
    }
}