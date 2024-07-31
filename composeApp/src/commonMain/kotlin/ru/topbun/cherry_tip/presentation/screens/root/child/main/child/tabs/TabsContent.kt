package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile.ProfileScreen

@Composable
fun TabsScreen(
    component: TabsComponent,
) {
    Children(component.stack){
        when(val instance = it.instance){
            is TabsComponent.Child.Home -> HomeScreen(instance.component)
            is TabsComponent.Child.Profile -> ProfileScreen(instance.component)
        }
    }

}