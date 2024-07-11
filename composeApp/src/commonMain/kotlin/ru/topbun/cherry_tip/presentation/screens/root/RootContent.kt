package ru.topbun.cherry_tip.presentation.screens.root

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import ru.topbun.cherry_tip.presentation.screens.auth.AuthContent
import ru.topbun.cherry_tip.presentation.screens.tabs.TabsScreen

@Composable
fun RootContent(
    component: RootComponent
) {
    Children(
        component.stack,
        animation = stackAnimation { child ->
            when(child.instance){
                is RootComponent.Child.Auth -> fade(tween(300)) + slide()
                RootComponent.Child.Tabs -> fade(tween(300)) + slide()
            }
        }
    ){
        when(val instance = it.instance){
            is RootComponent.Child.Auth -> AuthContent(instance.component)
            RootComponent.Child.Tabs -> TabsScreen()
        }
    }
}