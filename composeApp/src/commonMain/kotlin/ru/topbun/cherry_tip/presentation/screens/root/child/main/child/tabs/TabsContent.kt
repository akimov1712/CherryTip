package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal.AppendMealScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngestion.DetailIngestionScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.ProfileScreen
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun TabsScreen(
    component: TabsComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    Children(component.stack){
        when(val instance = it.instance){
            is TabsComponent.Child.Home -> HomeScreen(instance.component, modifier)
            is TabsComponent.Child.Calendar -> CalendarScreen(instance.component, modifier)
            is TabsComponent.Child.Settings -> ProfileScreen(instance.component, modifier)
            is TabsComponent.Child.Recipe -> RecipeScreen(instance.component, modifier)
        }
    }

}