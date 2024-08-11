package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_account
import cherrytip.composeapp.generated.resources.ic_goals
import cherrytip.composeapp.generated.resources.ic_units
import cherrytip.composeapp.generated.resources.ic_unlock
import cherrytip.composeapp.generated.resources.settings_account
import cherrytip.composeapp.generated.resources.settings_goals
import cherrytip.composeapp.generated.resources.settings_profile
import cherrytip.composeapp.generated.resources.settings_units
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Settings(
    val title: StringResource,
    val icon: DrawableResource
) {

    Account(title = Res.string.settings_account, icon = Res.drawable.ic_unlock),
    Profile(title = Res.string.settings_profile, icon = Res.drawable.ic_account),
    Goals(title = Res.string.settings_goals, icon = Res.drawable.ic_goals),
    Units(title = Res.string.settings_units, icon = Res.drawable.ic_units)

}