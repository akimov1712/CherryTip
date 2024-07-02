package ru.topbun.cherry_tip.presentation.screens.auth

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginContent

@Composable
fun AuthContent(
    component: AuthComponent,
) {
    Children(
        stack = component.stack
    ){
        when(val instance = it.instance){
            is AuthComponent.Child.Login -> LoginContent(instance.component)
        }
    }
}