package ru.topbun.cherry_tip.utills

fun Int.formatMinutesToTime(): String {
    val hours = this / 60
    val remainingMinutes = this % 60
    return when {
        hours > 0 && remainingMinutes > 0 -> "$hours h $remainingMinutes min"
        hours > 0 -> "$hours h"
        else -> "$remainingMinutes min"
    }
}