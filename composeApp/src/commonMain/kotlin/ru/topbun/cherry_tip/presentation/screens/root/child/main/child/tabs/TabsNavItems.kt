package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_nav_calendar
import cherrytip.composeapp.generated.resources.ic_nav_home
import cherrytip.composeapp.generated.resources.ic_nav_recipes
import cherrytip.composeapp.generated.resources.ic_nav_settings
import cherrytip.composeapp.generated.resources.ic_scale
import cherrytip.composeapp.generated.resources.nav_calendar
import cherrytip.composeapp.generated.resources.nav_home
import cherrytip.composeapp.generated.resources.nav_recipe
import cherrytip.composeapp.generated.resources.nav_settings
import cherrytip.composeapp.generated.resources.save
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class TabsNavItems(
    val config: TabsComponentImpl.Config,
    val iconRes: DrawableResource,
    val titleRes: StringResource,
    val onClickItem: () -> Unit
) {
    data class Home(private val clickItem: () -> Unit): TabsNavItems(
        config = TabsComponentImpl.Config.Home,
        iconRes = Res.drawable.ic_nav_home,
        titleRes = Res.string.nav_home,
        onClickItem = clickItem
    )

    data class Calendar(private val clickItem: () -> Unit): TabsNavItems(
        config = TabsComponentImpl.Config.Calendar,
        iconRes = Res.drawable.ic_nav_calendar,
        titleRes = Res.string.nav_calendar,
        onClickItem = clickItem
    )

    data class Recipes(private val clickItem: () -> Unit): TabsNavItems(
        config = TabsComponentImpl.Config.Recipe,
        iconRes = Res.drawable.ic_nav_recipes,
        titleRes = Res.string.nav_recipe,
        onClickItem = clickItem
    )

    data class Settings(private val clickItem: () -> Unit): TabsNavItems(
        config = TabsComponentImpl.Config.Settings,
        iconRes = Res.drawable.ic_nav_settings,
        titleRes = Res.string.nav_settings,
        onClickItem = clickItem
    )

}