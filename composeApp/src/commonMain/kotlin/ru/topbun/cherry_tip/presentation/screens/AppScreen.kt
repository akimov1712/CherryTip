package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.topbun.cherry_tip.presentation.screens.root.RootComponent
import ru.topbun.cherry_tip.presentation.screens.root.RootContent
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun AppScreen(rootComponent: RootComponent) {
        Surface {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.White)
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                RootContent(rootComponent)
            }
        }
}
