package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.have_account
import cherrytip.composeapp.generated.resources.login
import cherrytip.composeapp.generated.resources.welcome_cherry_tip
import cherrytip.composeapp.generated.resources.welcome_cherry_tip_descr
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.ErrorModal
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.presentation.ui.utills.getFileFromResource

@Composable
fun SplashScreen(
    component: SplashComponent,
    modifier: Modifier = Modifier.background(Colors.White)
) {
    val state by component.state.collectAsState()
    val showModal = state.splashState is SplashStore.State.SplashState.NotAuth
    Box(
        modifier = modifier.fillMaxSize().background(Colors.Purple)
    ) {
        Column {
            when(val screenState = state.splashState){
                is SplashStore.State.SplashState.Error -> {
                    ErrorModal(text = screenState.message){
                        component.runChecks()
                    }
                }
                else -> {}
            }
            Logo(component)
            AnimatedModal(showModal, component)
        }
    }
}

@Composable
private fun AnimatedModal(show: Boolean, component: SplashComponent) {
    val targetHeight = if (show) Modifier.wrapContentHeight() else Modifier.height(0.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .then(targetHeight)
            .animateContentSize(animationSpec = tween(500))
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (show) {
            Spacer(Modifier.height(24.dp))
            ModalText()
            Spacer(Modifier.height(20.dp))
            ButtonList(component)
            Spacer(Modifier.height(20.dp))
            TextHaveAccount(component)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TextHaveAccount(component: SplashComponent) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Texts.General(stringResource(Res.string.have_account))
        Texts.Link(stringResource(Res.string.login), onClick = { component.onLogin() })
    }
}

@Composable
private fun ButtonList(component: SplashComponent) {
    val authItems = listOf(AuthItems.Apple, AuthItems.Facebook, AuthItems.Google, AuthItems.Email)
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        authItems.forEach {
            ButtonAuth(
                text = stringResource(it.textRes),
                icon = painterResource(it.iconRes)
            ) {
                when(it){
                    AuthItems.Apple -> {}
                    AuthItems.Facebook -> {}
                    AuthItems.Google -> {}
                    AuthItems.Email -> { component.onSignUpEmail() }
                }
            }
        }
    }
}

@Composable
private fun ButtonAuth(text: String, icon: Painter, onClick: () -> Unit) {
    Buttons.Gray(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        onClick = onClick,
    ) {
        IconWithText(text, icon)
    }
}

@Composable
private fun IconWithText(text: String, icon: Painter) {
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
        Texts.Button(text, fontSize = 16.sp, color = Colors.Purple)
    }
}

@Composable
private fun ModalText() {
    Texts.Title(stringResource(Res.string.welcome_cherry_tip), fontSize = 30.sp)
    Spacer(Modifier.height(20.dp))
    Texts.General(stringResource(Res.string.welcome_cherry_tip_descr), textAlign = TextAlign.Center)
}

@Composable
private fun ColumnScope.Logo(component: SplashComponent) {
    val lottie by getFileFromResource("files/anim_splash_logo.json")
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(lottie))
    val progress by animateLottieCompositionAsState(composition)
    LaunchedEffect(progress) {
        if (progress >= 1f) {
            component.runChecks()
        }
    }
    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        composition = composition,
        progress = { progress }
    )
}
