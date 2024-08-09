package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.profile_city
import cherrytip.composeapp.generated.resources.profile_date_birth
import cherrytip.composeapp.generated.resources.profile_first_name
import cherrytip.composeapp.generated.resources.profile_last_name
import cherrytip.composeapp.generated.resources.profile_sex
import org.jetbrains.compose.resources.StringResource

enum class ProfileObjects(
    val title: StringResource
) {

    FirstName(Res.string.profile_first_name),
    LastName(Res.string.profile_last_name),
    City(Res.string.profile_city),
    Sex(Res.string.profile_sex),
    DateBirth(Res.string.profile_date_birth),

}