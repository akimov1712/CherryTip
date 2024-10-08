package ru.topbun.cherry_tip.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.koin.compose.KoinContext
import org.koin.compose.getKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.topbun.cherry_tip.presentation.screens.root.RootComponent
import ru.topbun.cherry_tip.presentation.screens.root.RootContent
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun AppScreen(rootComponent: RootComponent) {
    KoinContext {
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
}
