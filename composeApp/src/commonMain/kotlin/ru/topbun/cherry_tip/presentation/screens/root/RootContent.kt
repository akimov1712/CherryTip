package ru.topbun.cherry_tip.presentation.screens.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.topbun.cherry_tip.presentation.screens.auth.AuthContent

@Composable
fun RootContent(
    component: RootComponent
) {
    Children(component.stack){
        when(val instance = it.instance){
            is RootComponent.Child.Auth -> AuthContent(instance.component)
        }
    }
}