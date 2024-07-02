package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.KoinContext
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponent
import ru.topbun.cherry_tip.presentation.screens.auth.AuthContent
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun AppScreen(authComponent: AuthComponent) {
    KoinContext {
        Scaffold(
            modifier = Modifier
                .background(Colors.White)
                .navigationBarsPadding()
        ) {
            AuthContent(
                component = authComponent
            )
        }
    }
}