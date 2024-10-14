package ru.topbun.cherry_tip

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import ru.topbun.cherry_tip.presentation.screens.AppScreen
import ru.topbun.cherry_tip.presentation.screens.root.RootComponentImpl
import ru.topbun.cherry_tip.presentation.ui.Colors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = RootComponentImpl(defaultComponentContext())
        setContent {
            requestPermissionNotify()
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

    @Composable
    private fun requestPermissionNotify(){
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val launcher = rememberLauncherForActivityResult(contract = contract) { }
        SideEffect {
            launcher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SET_ALARM) )
        }
    }
}
