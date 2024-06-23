package ru.topbun.cherry_tip

import ru.topbun.cherry_tip.presentation.screens.AppScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TransparentStatusBar()
            AppScreen()
        }
    }
}

@Composable
private fun TransparentStatusBar() {
    val context = LocalContext.current
    val view = LocalView.current
    LaunchedEffect(Unit) {
        val window = (context as ComponentActivity).window
        WindowInsetsControllerCompat(window, view).apply {
            isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
            window.statusBarColor = Color.Transparent.toArgb()
        }
    }
}
