package ru.topbun.cherry_tip

import android.app.Activity
import android.os.Build
import ru.topbun.cherry_tip.presentation.screens.AppScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.RootComponentImpl
import ru.topbun.cherry_tip.presentation.ui.Colors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = RootComponentImpl(defaultComponentContext())
        setContent {
            val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    window.statusBarColor = Colors.White.toArgb()
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                }
            }
            MaterialTheme {
                AppScreen(rootComponent)
            }
        }
    }
}
