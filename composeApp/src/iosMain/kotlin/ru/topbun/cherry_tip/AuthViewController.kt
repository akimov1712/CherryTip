package ru.topbun.cherry_tip

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ru.topbun.cherry_tip.presentation.screens.AppScreen
import ru.topbun.cherry_tip.presentation.screens.root.RootComponent

fun rootViewController(root: RootComponent): UIViewController =
    ComposeUIViewController {
        AppScreen(root)
    }