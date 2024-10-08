package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsComponent

interface TabsComponent {

    val stack: Value<ChildStack<*, Child>>

    fun openHome()
    fun openCalendar()
    fun openRecipe()
    fun openSettings()

    sealed interface Child {
        data class Home(val component: HomeComponent) : Child
        data class Calendar(val component: CalendarComponent) : Child
        data class Recipe(val component: RecipeComponent) : Child
        data class Settings(val component: SettingsComponent) : Child
    }

}