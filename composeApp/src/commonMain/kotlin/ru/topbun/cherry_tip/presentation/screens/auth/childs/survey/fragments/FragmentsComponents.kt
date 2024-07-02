package ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.fragments

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.continue_string
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_info
import cherrytip.composeapp.generated.resources.ic_pointer
import cherrytip.composeapp.generated.resources.warning_about_changing_values
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.Fonts
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import kotlin.math.abs
import kotlin.math.round

object FragmentsComponents{

    @Composable
    fun ButtonsNavigation(
        onClickBack: () -> Unit = {},
        onClickNext: () -> Unit = {},
        nextButtonText: String = stringResource(Res.string.continue_string),
        isEnableBack: Boolean = true
    ) {
        Row {
            if (isEnableBack){
                Buttons.Purple(
                    modifier = Modifier.size(60.dp),
                    onClick = onClickBack,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        tint = Colors.White
                    )
                }
                Spacer(Modifier.width(10.dp))
            }
            Buttons.Purple(
                modifier = Modifier.height(60.dp).fillMaxWidth(),
                onClick = onClickNext,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Purple,
                    disabledContainerColor = Colors.Purple.copy(alpha = 0.3f)
                )
            ) {
                Texts.Button(
                    nextButtonText,
                )
            }
        }
    }

    @Composable
    fun IconWithText(
        icon: Painter,
        text: String,
        color: Color
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = icon,
            contentDescription = text,
            tint = color
        )
        Spacer(Modifier.width(12.dp))
        Texts.Button(text, color = color, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
    }

    @Composable
    fun FragmentWrapper(
        modifier: Modifier = Modifier,
        title: String,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texts.Title(title)
            Spacer(Modifier.height(40.dp))
            content()
        }
    }

    @Composable
    fun TextField(
        modifier: Modifier = Modifier,
        text: String,
        onValueChange: (String) -> Unit,
        placeholder: String = "",
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextStyle(
            color = Colors.GrayDark,
            fontSize = 40.sp,
            fontFamily = Fonts.hovesMedium,
            textAlign = TextAlign.Center
        ),
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = true,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        minLines: Int = 1,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onTextLayout: (TextLayoutResult) -> Unit = {},
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        cursorBrush: Brush = SolidColor(Colors.GrayDark),
    ) {
        Box (
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            if (text.isBlank()) Texts.Placeholder(placeholder)
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                visualTransformation = visualTransformation,
                onTextLayout = onTextLayout,
                interactionSource = interactionSource,
                cursorBrush = cursorBrush,
            )
        }
    }

    @Composable
    fun NumberSlidePicker(
        modifier: Modifier = Modifier,
        minValue: Int,
        maxValue: Int,
        unit: String,
        onValueChange: (Int) -> Unit
    ) {
        var difference by remember { mutableStateOf(0) }
        val numbers = (minValue - difference..maxValue + difference).toList()
        val state = rememberLazyListState(numbers.size / 2)
        var centerValue by remember { mutableIntStateOf(numbers[numbers.size / 2]) }
        val visibleSize by remember {
            derivedStateOf {
                val visibleSize = state.layoutInfo.visibleItemsInfo.size
                difference = round(visibleSize / 2f).toInt() - 1
                visibleSize
            }
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text(
                    text = centerValue.toString(),
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(900),
                    fontSize = 40.sp
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(32.dp, -6.dp),
                    text = unit,
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(500),
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            LazyRow(
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 55.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(items = numbers) { number ->
                    val centerIndex = state.firstVisibleItemIndex + (visibleSize / 2)
                    val centerItemValue = numbers.getOrNull(centerIndex) ?: numbers.last()

                    if (number == centerItemValue) {
                        centerValue = number
                        onValueChange(centerValue)
                    }

                    val index = numbers.indexOf(number)
                    val remotely = abs(centerIndex - index)

                    val size = when {
                        number % 10 == 0 && remotely <= 0 -> Size(6.6f, 55f)
                        number % 5 == 0 -> Size(5.5f, 40f)
                        else -> Size(3f, 25f)
                    }
                    val animateSize by animateSizeAsState(targetValue = size, tween(durationMillis = 300))

                    val color = Color(114, 101, 227)
                    val background = when {
                        number % 10 == 0 && remotely <= 0 -> color.copy(alpha = 1f)
                        number % 10 == 0 -> color.copy(alpha = 0.7f)
                        number % 5 == 0 && remotely <= 0 -> color.copy(alpha = 0.7f)
                        else -> color.copy(alpha = 0.3f)
                    }
                    val animateBackground by animateColorAsState(targetValue = background)

                    Column {
                        Box(
                            modifier = Modifier
                                .size(animateSize.width.dp, animateSize.height.dp)
                                .background(animateBackground, CircleShape)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_pointer),
                contentDescription = null,
                tint = Color(114, 101, 227)
            )
        }
    }

    @Composable
    fun WarningAboutChangingValues() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.PurpleBackground, RoundedCornerShape(16.dp))
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_info),
                contentDescription = null,
                tint = Colors.GrayDark
            )
            Spacer(Modifier.width(10.dp))
            Texts.Option(
                text = stringResource(Res.string.warning_about_changing_values),
                color = Colors.GrayDark,
                textAlign = TextAlign.Start
            )
        }
    }

}