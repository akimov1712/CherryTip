package ru.topbun.cherry_tip

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ru.topbun.cherry_tip.presentation.screens.AppScreen
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponent

fun authViewController(auth: AuthComponent): UIViewController =
    ComposeUIViewController {
        AppScreen(auth)
    }