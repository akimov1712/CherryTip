package ru.topbun.cherry_tip.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.compose_multiplatform
import cherrytip.composeapp.generated.resources.have_account
import cherrytip.composeapp.generated.resources.ic_apple
import cherrytip.composeapp.generated.resources.login
import cherrytip.composeapp.generated.resources.welcome_cherry_tip
import cherrytip.composeapp.generated.resources.welcome_cherry_tip_descr
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Text
import ru.topbun.cherry_tip.presentation.ui.utills.animateWrapContentHeight
import ru.topbun.cherry_tip.presentation.ui.utills.getFileFromResource

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(Colors.Purple),
        contentAlignment = Alignment.Center
    ) {
        Column {
            var isAnimate = remember { mutableStateOf(false) }
            Logo(isAnimate)
            Modal(isAnimate)
        }
    }
}

@Composable
private fun Modal(isAnimate: MutableState<Boolean>) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Colors.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .animateWrapContentHeight(isAnimate.value)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        ModalText()
        Spacer(Modifier.height(20.dp))
        ButtonList()
        Spacer(Modifier.height(20.dp))
        TextHaveAccount()
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun TextHaveAccount() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text.General(stringResource(Res.string.have_account))
        Text.Link(stringResource(Res.string.login))
    }
}

@Composable
private fun ButtonList() {
    val authItems = listOf(AuthItems.Apple, AuthItems.Facebook, AuthItems.Google, AuthItems.Email)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = authItems, key = { it.textRes.key }) {
            ButtonAuth(
                text = stringResource(it.textRes),
                icon = painterResource(it.iconRes)
            ) {

            }
        }
    }
}

@Composable
private fun ButtonAuth(text: String, icon: Painter, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.PurpleBackground),
        elevation = null,
        onClick = onClick,
    ) {
        IconWithText(text, icon)
    }
}

@Composable
fun IconWithText(text: String, icon: Painter) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            painter = icon,
            contentDescription = text,
            tint = Colors.Purple
        )
        Spacer(Modifier.width(12.dp))
        Text.Button(text, fontSize = 16.sp, color = Colors.Purple)
    }
}

@Composable
private fun ModalText() {
    Text.Title(stringResource(Res.string.welcome_cherry_tip), fontSize = 30.sp)
    Spacer(Modifier.height(20.dp))
    Text.General(stringResource(Res.string.welcome_cherry_tip_descr), textAlign = TextAlign.Center)
}


@Composable
private fun ColumnScope.Logo(isAnimate: MutableState<Boolean>) {
    val lottie by getFileFromResource("files/anim_splash_logo.json")
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(lottie))
    val progress by animateLottieCompositionAsState(composition)
    LaunchedEffect(progress) {
        if (progress >= 1f) isAnimate.value = true
    }
    LottieAnimation(
        modifier = Modifier.fillMaxWidth().weight(1f),
        composition = composition,
        progress = { progress }
    )
}

