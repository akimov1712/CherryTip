package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.topbun.cherry_tip.presentation.screens.auth.login.LoginContent
import ru.topbun.cherry_tip.presentation.screens.auth.signUp.SignUpContent
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun AppScreen() {
    Scaffold(
        modifier = Modifier
            .background(Colors.White)
            .navigationBarsPadding()
    ) {
        LoginContent()
    }
}