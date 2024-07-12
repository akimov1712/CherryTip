package ru.topbun.cherry_tip.presentation.screens.root.child.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponent

interface MainComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Tabs(val component: TabsComponent): Child
    }


}