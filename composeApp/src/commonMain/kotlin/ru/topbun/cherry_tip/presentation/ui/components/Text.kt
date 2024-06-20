package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.hovesBoldFont
import ru.topbun.cherry_tip.presentation.ui.hovesMediumFont
import ru.topbun.cherry_tip.presentation.ui.hovesRegularFont

object Text{

    @Composable
    fun Title(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Black,
        fontSize: TextUnit = 22.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = hovesBoldFont,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

    @Composable
    fun General(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Gray,
        fontSize: TextUnit = 16.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = hovesRegularFont,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

    @Composable
    fun Option(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Purple,
        fontSize: TextUnit = 16.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = hovesMediumFont,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

    @Composable
    fun Button(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.GrayLight,
        fontSize: TextUnit = 18.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = hovesMediumFont,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

    @Composable
    fun Link(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Purple,
        fontSize: TextUnit = 16.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = hovesRegularFont,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = TextDecoration.Underline,
        textAlign: TextAlign? = null,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

}