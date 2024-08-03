package ru.topbun.cherry_tip.presentation.screens.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Auth(val component: AuthComponent): Child
        data class Main(val component: MainComponent): Child
    }

}