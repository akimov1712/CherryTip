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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import cherrytip.composeapp.generated.resources.have_account
import cherrytip.composeapp.generated.resources.ic_apple
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_facebook
import cherrytip.composeapp.generated.resources.ic_google
import cherrytip.composeapp.generated.resources.ic_hide
import cherrytip.composeapp.generated.resources.ic_show
import cherrytip.composeapp.generated.resources.login
import cherrytip.composeapp.generated.resources.or_login
import cherrytip.composeapp.generated.resources.password
import cherrytip.composeapp.generated.resources.login_descr
import cherrytip.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.domain.repository.AuthRepository
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
    val snackBarHostState = SnackbarHostState()
    LaunchedEffect(state){
        when(val loginState = state.loginState){
            is LoginStore.State.LoginState.Error -> snackBarHostState.showSnackbar(loginState.errorText)
            else -> {}
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
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
                onChangeValidPassword = component::changeValidPassword
            )
            Spacer(Modifier.height(40.dp))
            ButtonLogin(state.isValidPassword){
                component.onLogin(
                    LoginEntity(state.email, state.password)
                )
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
        Texts.Link(stringResource(Res.string.sign_up),
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
            ){
                onClickSignUp()
            }
        )
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
    isVisiblePassword: Boolean,
    onLogin: () -> Unit
) {
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        onClick = onLogin,
        enabled = isVisiblePassword
    ) { Texts.Button(stringResource(Res.string.login)) }
}

@Composable
fun LoginFields(
    email: String,
    password: String,
    isVisiblePassword: Boolean,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeVisiblePassword: (Boolean) -> Unit,
    onChangeValidPassword: (Boolean) -> Unit,
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            trailingIcon = {
                IconButton(
                    onClick = {onChangeVisiblePassword(!isVisiblePassword)}
                ){ Icon(
                    painterResource(if(isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )

    }

    onChangeValidPassword(email.isNotBlank() && password.isNotBlank())
}