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
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType

enum class CalendarObjects(
    val icon: DrawableResource,
    val title: StringResource,
    val type: CalendarType
) {

    Breakfast(Res.drawable.ic_breakfast, Res.string.breakfast, CalendarType.Breakfast),
    Lunch(Res.drawable.ic_lunch, Res.string.lunch, CalendarType.Lunch),
    Dinner(Res.drawable.ic_dinner, Res.string.dinner, CalendarType.Dinner),
    Snack(Res.drawable.ic_snack, Res.string.snack, CalendarType.Snack),

}