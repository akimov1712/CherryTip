package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import org.koin.compose.KoinContext
import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponent
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.AuthContent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginContent
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpContent
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