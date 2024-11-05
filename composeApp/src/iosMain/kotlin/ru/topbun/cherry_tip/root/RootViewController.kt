package ru.topbun.cherry_tip.root

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.decompose.DefaultComponentContext
import platform.UIKit.UIViewController
import ru.topbun.cherry_tip.presentation.screens.AppScreen
import ru.topbun.cherry_tip.presentation.screens.root.RootComponentImpl

fun RootViewController(): UIViewController {

    val lifecycle = LifecycleRegistry()

    val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)
    val component = RootComponentImpl(rootComponentContext)

    return ComposeUIViewController {
        AppScreen(component)
    }
}