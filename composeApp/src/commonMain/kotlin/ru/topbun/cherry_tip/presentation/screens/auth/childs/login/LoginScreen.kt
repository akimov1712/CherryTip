package ru.topbun.cherry_tip.presentation.screens.auth.childs.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.dont_have_account
import cherrytip.composeapp.generated.resources.email
import cherrytip.composeapp.generated.resources.ic_apple
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_facebook
import cherrytip.composeapp.generated.resources.ic_google
import cherrytip.composeapp.generated.resources.ic_hide
import cherrytip.composeapp.generated.resources.ic_show
import cherrytip.composeapp.generated.resources.login
import cherrytip.composeapp.generated.resources.login_descr
import cherrytip.composeapp.generated.resources.or_login
import cherrytip.composeapp.generated.resources.password
import cherrytip.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.auth.LoginEntity
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.LoginFields
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts


@Composable
fun LoginContent(
    component: LoginComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val state by component.state.collectAsState()
    var errorText by rememberSaveable{ mutableStateOf<String?>(null) }
    LaunchedEffect(state.loginState){
        errorText = when(val loginState = state.loginState){
            is LoginStore.State.LoginState.Error -> loginState.errorText
            else -> null
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Colors.White)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Buttons.Icon(
            painterResource(Res.drawable.ic_back),
            Modifier.size(60.dp)
        ) {component.clickBack()}
        Spacer(Modifier.height(40.dp))
        Texts.Title(
            stringResource(Res.string.login_descr),
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(40.dp))
        LoginFields(
            email = state.email,
            password = state.password,
            isVisiblePassword = state.isVisiblePassword,
            onChangeEmail = component::changeEmail,
            onChangePassword = component::changePassword,
            onChangeVisiblePassword = component::changeVisiblePassword,
        )
        Spacer(Modifier.height(20.dp))
        errorText?.let { Texts.Error(it) }
        Spacer(Modifier.height(20.dp))
        ButtonLogin(
            isEnabled = state.email.isNotBlank() && state.password.isNotBlank() &&
                    state.loginState != LoginStore.State.LoginState.Loading,
            isLoading = state.loginState == LoginStore.State.LoginState.Loading
        ){
            component.onLogin()
        }
        Spacer(Modifier.height(40.dp))
        SeparateText()
        Spacer(Modifier.height(20.dp))
        AuthMethods()
        Spacer(Modifier.weight(1f))
        TextDontHaveAccount{ component.clickSignUp() }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun TextDontHaveAccount(
    onClickSignUp: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Texts.General(stringResource(Res.string.dont_have_account))
        Texts.Link(stringResource(Res.string.sign_up), onClick = onClickSignUp)
    }
}

@Composable
private fun AuthMethods() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Buttons.Icon(
            painterResource(Res.drawable.ic_google),
            Modifier.height(60.dp).weight(1f)
        ) { }
        Buttons.Icon(
            painterResource(Res.drawable.ic_apple),
            Modifier.height(60.dp).weight(1f)
        ) { }
        Buttons.Icon(
            painterResource(Res.drawable.ic_facebook),
            Modifier.height(60.dp).weight(1f)
        ) { }
    }
}

@Composable
private fun SeparateText() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.fillMaxWidth().height(2.dp).background(Colors.PurpleBackground, CircleShape))
        Texts.General(
            modifier = Modifier.background(Colors.White).padding(horizontal = 10.dp),
            text = stringResource(Res.string.or_login),
        )
    }
}

@Composable
private fun ButtonLogin(
    isEnabled: Boolean,
    isLoading: Boolean,
    onLogin: () -> Unit
) {
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        onClick = onLogin,
        enabled = isEnabled,
    ) {
        if (isLoading) CircularProgressIndicator(color = Colors.GrayLight, modifier = Modifier.size(24.dp), strokeCap = StrokeCap.Round, strokeWidth = 3.dp)
        else Texts.Button(stringResource(Res.string.login))
    }
}

@Composable
fun LoginFields(
    email: String,
    password: String,
    isVisiblePassword: Boolean,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeVisiblePassword: (Boolean) -> Unit,
){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        TextFields.OutlinedTextField(
            value = email,
            onValueChange = {
                onChangeEmail(it)
            },
            placeholderText = stringResource(Res.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )
        TextFields.OutlinedTextField(
            value = password,
            onValueChange = {
                onChangePassword(it)
            },
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            placeholderText = stringResource(Res.string.password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(
                    onClick = { onChangeVisiblePassword(!isVisiblePassword)}
                ){ Icon(
                    painterResource(if(isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )

    }
}