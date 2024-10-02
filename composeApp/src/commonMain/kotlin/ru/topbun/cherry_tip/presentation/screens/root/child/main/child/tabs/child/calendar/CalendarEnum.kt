package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.breakfast
import cherrytip.composeapp.generated.resources.dinner
import cherrytip.composeapp.generated.resources.ic_breakfast
import cherrytip.composeapp.generated.resources.ic_dinner
import cherrytip.composeapp.generated.resources.ic_lunch
import cherrytip.composeapp.generated.resources.ic_snack
import cherrytip.composeapp.generated.resources.lunch
import cherrytip.composeapp.generated.resources.snack
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class CalendarEnum(
    val icon: DrawableResource,
    val title: StringResource
) {

    Breakfast(Res.drawable.ic_breakfast, Res.string.breakfast),
    Lunch(Res.drawable.ic_lunch, Res.string.lunch),
    Dinner(Res.drawable.ic_dinner, Res.string.dinner),
    Snack(Res.drawable.ic_snack, Res.string.snack),

}