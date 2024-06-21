package ru.topbun.cherry_tip.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.hoves_bold
import cherrytip.composeapp.generated.resources.hoves_light
import cherrytip.composeapp.generated.resources.hoves_medium
import cherrytip.composeapp.generated.resources.hoves_regular
import cherrytip.composeapp.generated.resources.sf_bold
import cherrytip.composeapp.generated.resources.sf_regular
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource

@Composable
fun createFont(fontRes: FontResource) = FontFamily(Font(fontRes))

object Fonts{

    val hovesBold
        @Composable get() = createFont(Res.font.hoves_bold)

    val hovesLight
        @Composable get() = createFont(Res.font.hoves_light)

    val hovesMedium
        @Composable get() = createFont(Res.font.hoves_medium)

    val hovesRegular
        @Composable get() = createFont(Res.font.hoves_regular)

    val sfBold
        @Composable get() = createFont(Res.font.sf_bold)

    val sfRegular
        @Composable get() = createFont(Res.font.sf_regular)

}
