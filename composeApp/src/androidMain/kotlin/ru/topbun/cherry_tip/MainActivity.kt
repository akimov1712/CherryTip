package ru.topbun.cherry_tip

import ru.topbun.cherry_tip.presentation.screens.AppScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.arkivanov.decompose.defaultComponentContext
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.RootComponentImpl

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val rootComponent = RootComponentImpl(defaultComponentContext())
        setContent {
            MaterialTheme {
                AppScreen(rootComponent)
            }
        }
    }
}
