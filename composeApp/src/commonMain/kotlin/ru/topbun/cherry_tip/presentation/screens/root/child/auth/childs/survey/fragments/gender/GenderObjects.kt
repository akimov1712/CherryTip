package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.female
import cherrytip.composeapp.generated.resources.ic_female
import cherrytip.composeapp.generated.resources.ic_male
import cherrytip.composeapp.generated.resources.male
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class GenderObjects(
    val gender: Gender,
    val textRes: StringResource,
    val iconRes: DrawableResource,
) {

    data object Female: GenderObjects(Gender.Female, Res.string.female, Res.drawable.ic_female)
    data object Male: GenderObjects(Gender.Male, Res.string.male, Res.drawable.ic_male)

}