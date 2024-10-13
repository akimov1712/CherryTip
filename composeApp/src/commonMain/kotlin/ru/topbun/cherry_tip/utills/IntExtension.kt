package ru.topbun.cherry_tip.utills

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.alone_day
import cherrytip.composeapp.generated.resources.declination_many_days
import cherrytip.composeapp.generated.resources.declination_single_days
import cherrytip.composeapp.generated.resources.many_days
import cherrytip.composeapp.generated.resources.single_day
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun Int.formatMinutesToTime(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return when {
        hours > 0 && remainingMinutes > 0 -> "$hours h $remainingMinutes min"
        hours > 0 -> "$hours h"
        else -> "$remainingMinutes min"
    }
}

fun Int.getResourceEndingDays(): StringResource {
    val preLastDigit: Int = this % 100 / 10
    if (preLastDigit == 1) {
        return (Res.string.many_days)
    }
    return when (this % 10) {
        1 -> (Res.string.single_day)
        2, 3, 4 -> (Res.string.alone_day)
        else -> (Res.string.many_days)
    }
}

fun Int.getResourceDeclinationEndingDays(): StringResource {
    return if (this == 1) Res.string.declination_single_days else Res.string.declination_many_days
}