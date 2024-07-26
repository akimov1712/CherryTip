package ru.topbun.cherry_tip.presentation.ui.utills

import androidx.compose.ui.graphics.Color

fun hexToColor(hex: String): Color {
    val colorString = hex.removePrefix("#")
    val colorInt = colorString.toLong(16)
    return if (colorString.length == 6) {
        Color((0xFF000000 or colorInt).toInt())
    } else {
        Color(colorInt.toInt())
    }
}