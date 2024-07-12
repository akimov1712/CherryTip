package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.reminder1
import cherrytip.composeapp.generated.resources.reminder2
import cherrytip.composeapp.generated.resources.reminder3
import cherrytip.composeapp.generated.resources.reminder_descr_1
import cherrytip.composeapp.generated.resources.reminder_descr_2
import cherrytip.composeapp.generated.resources.reminder_descr_3
import cherrytip.composeapp.generated.resources.reminder_title_1
import cherrytip.composeapp.generated.resources.reminder_title_2
import cherrytip.composeapp.generated.resources.reminder_title_3
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class ReminderScreens(
    val titleRes: StringResource,
    val descrRes: StringResource,
    val imageRes: DrawableResource,
) {

    data object Reminder1: ReminderScreens(
        titleRes = Res.string.reminder_title_1,
        descrRes = Res.string.reminder_descr_1,
        imageRes = Res.drawable.reminder1
    )

    data object Reminder2: ReminderScreens(
        titleRes = Res.string.reminder_title_2,
        descrRes = Res.string.reminder_descr_2,
        imageRes = Res.drawable.reminder2
    )

    data object Reminder3: ReminderScreens(
        titleRes = Res.string.reminder_title_3,
        descrRes = Res.string.reminder_descr_3,
        imageRes = Res.drawable.reminder3
    )

}