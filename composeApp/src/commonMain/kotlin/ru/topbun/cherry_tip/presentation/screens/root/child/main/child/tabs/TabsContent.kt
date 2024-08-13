package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.ProfileScreen

@Composable
fun TabsScreen(
    component: TabsComponent,
) {
    Children(component.stack){
        when(val instance = it.instance){
            is TabsComponent.Child.Home -> HomeScreen(instance.component)
            is TabsComponent.Child.Settings -> ProfileScreen(instance.component)
            is TabsComponent.Child.Recipe -> RecipeScreen(instance.component)
        }
    }

}