package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
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
import ru.topbun.cherry_tip.presentation.ui.Fonts

object Texts{

    @Composable
    fun Title(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Black,
        fontSize: TextUnit = 22.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesBold,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
        lineHeight: TextUnit = 28.sp,
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
        fontFamily: FontFamily? = Fonts.hovesRegular,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
        fontSize: TextUnit = 18.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesMedium,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
    fun Light(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Gray,
        fontSize: TextUnit = 14.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesLight,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
    fun Tips(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Gray,
        fontSize: TextUnit = 14.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesRegular,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
        fontFamily: FontFamily? = Fonts.hovesMedium,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        color: Color = Colors.Purple,
        fontSize: TextUnit = 16.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesRegular,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = TextDecoration.Underline,
        textAlign: TextAlign? = TextAlign.Center,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text,modifier.clickable (interactionSource = MutableInteractionSource(), indication = null, onClick = onClick),color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

    @Composable
    fun Placeholder(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Colors.Gray,
        fontSize: TextUnit = 40.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesMedium,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Center,
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
    fun Error(
        text: String?,
        modifier: Modifier = Modifier,
        color: Color = Colors.Red,
        fontSize: TextUnit = 16.sp,
        fontStyle: FontStyle? = null,
        fontWeight: FontWeight? = null,
        fontFamily: FontFamily? = Fonts.hovesMedium,
        letterSpacing: TextUnit = TextUnit.Unspecified,
        textDecoration: TextDecoration? = null,
        textAlign: TextAlign? = TextAlign.Start,
        lineHeight: TextUnit = TextUnit.Unspecified,
        overflow: TextOverflow = TextOverflow.Clip,
        softWrap: Boolean = true,
        maxLines: Int = Int.MAX_VALUE,
        minLines: Int = 1,
        onTextLayout: ((TextLayoutResult) -> Unit)? = null,
        style: TextStyle = LocalTextStyle.current
    ) = Text(
        text ?: "",modifier,color,fontSize,fontStyle,fontWeight,fontFamily,letterSpacing,textDecoration,
        textAlign,lineHeight,overflow,softWrap,maxLines,minLines,onTextLayout,style
    )

}