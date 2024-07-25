package ru.topbun.cherry_tip.presentation.screens.root

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainScreen

@Composable
fun RootContent(
    component: RootComponent
) {
    Children(
        component.stack,
        animation = stackAnimation { child ->
            when(child.instance){
                is RootComponent.Child.Auth -> defaultAnimationScreen
                is RootComponent.Child.Main -> defaultAnimationScreen
            }
        }
    ){
        when(val instance = it.instance){
            is RootComponent.Child.Auth -> AuthScreen(instance.component)
            is RootComponent.Child.Main -> MainScreen(instance.component)
        }
    }
}

private val defaultAnimationScreen = fade(tween(300)) + slide()