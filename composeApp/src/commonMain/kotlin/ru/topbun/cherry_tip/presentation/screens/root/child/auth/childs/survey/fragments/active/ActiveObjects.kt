package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.active_descr
import cherrytip.composeapp.generated.resources.active_title
import cherrytip.composeapp.generated.resources.inactive_descr
import cherrytip.composeapp.generated.resources.inactive_title
import cherrytip.composeapp.generated.resources.moderate_descr
import cherrytip.composeapp.generated.resources.moderate_title
import org.jetbrains.compose.resources.StringResource

enum class ActiveObjects(
    val type: ActiveType,
    val titleRes: StringResource,
    val descrRes: StringResource,
) {

    Inactive(ActiveType.Low, Res.string.inactive_title, Res.string.inactive_descr),
    Moderate(ActiveType.Medium, Res.string.moderate_title, Res.string.moderate_descr),
    Active(ActiveType.High, Res.string.active_title, Res.string.active_descr),

}