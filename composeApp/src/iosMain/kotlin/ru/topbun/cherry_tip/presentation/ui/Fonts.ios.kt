package ru.topbun.cherry_tip.presentation.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.platform.Typeface
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

private fun loadCustomFont(name: String): Typeface {
    return Typeface.makeFromName(name, FontStyle.NORMAL)
}

actual val hovesBoldFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))
actual val hovesLightFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))
actual val hovesRegularFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))
actual val hovesMediumFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))

actual val sfRegularFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))
actual val sfBoldFont: FontFamily = FontFamily(Typeface(loadCustomFont("hoves_bold")))