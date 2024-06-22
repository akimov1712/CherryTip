package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.topbun.cherry_tip.presentation.screens.reminder.ReminderScreen
import ru.topbun.cherry_tip.presentation.screens.splash.SplashScreen

@Composable
fun AppScreen() {
    MaterialTheme {
        ReminderScreen()
    }
}