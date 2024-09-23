package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import cherrytip.composeapp.generated.resources.confirm_password
import cherrytip.composeapp.generated.resources.confirm_password_error
import cherrytip.composeapp.generated.resources.email
import cherrytip.composeapp.generated.resources.email_error
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
import cherrytip.composeapp.generated.resources.password_error
import cherrytip.composeapp.generated.resources.sign_up
import cherrytip.composeapp.generated.resources.sign_up_descr
import cherrytip.composeapp.generated.resources.username
import cherrytip.composeapp.generated.resources.username_error
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.validEmail


@Composable
fun SignUpScreen(
    component: SignUpComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    val state by component.state.collectAsState()
    var errorText by rememberSaveable { mutableStateOf<String?>(null) }
    LaunchedEffect(state.signUpState) {
        errorText = when (val loginState = state.signUpState) {
            is SignUpStore.State.SignUpState.Error -> loginState.errorText
            else -> null
        }
    }
    Column(
        modifier = modifier
            .background(Colors.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Buttons.Icon(
            painterResource(Res.drawable.ic_back),
            Modifier.size(60.dp)
        ) { component.clickBack() }
        Spacer(Modifier.height(40.dp))
        Texts.Title(
            stringResource(Res.string.sign_up_descr),
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(40.dp))
        SignUpFields(component, state)
        Spacer(Modifier.height(20.dp))
        errorText?.let { Texts.Error(it) }
        Spacer(Modifier.height(20.dp))
        ButtonSignUp(
            isValid = isValidSignUp(state),
            isLoading = state.signUpState == SignUpStore.State.SignUpState.Loading
        ) { component.onSignUp() }
        Spacer(Modifier.height(40.dp))
        SeparateText()
        Spacer(Modifier.height(20.dp))
        AuthMethods()
        Spacer(Modifier.height(40.dp))
        TextHaveAccount { component.clickLogin() }
        Spacer(Modifier.height(20.dp))
    }
}


private fun isValidSignUp(state: SignUpStore.State) =
    !state.usernameIsError &&
    !state.emailIsError &&
    !state.passwordIsError &&
    !state.confirmPasswordIsError &&
    state.username.isNotBlank() &&
    state.email.isNotBlank() &&
    state.password.isNotBlank() &&
    state.confirmPassword.isNotBlank() && state.signUpState != SignUpStore.State.SignUpState.Loading

@Composable
private fun TextHaveAccount(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Texts.General(stringResource(Res.string.have_account))
        Texts.Link(stringResource(Res.string.login), onClick = onClick)
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
private fun ButtonSignUp(
    isValid: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        onClick = onClick,
        enabled = isValid
    ) {
        if (isLoading) CircularProgressIndicator(color = Colors.GrayLight, modifier = Modifier.size(24.dp), strokeCap = StrokeCap.Round, strokeWidth = 3.dp)
        else Texts.Button(stringResource(Res.string.sign_up))
    }
}

@Composable
fun SignUpFields(
    component: SignUpComponent,
    state: SignUpStore.State
){
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextFields.OutlinedTextField(
            value = state.username,
            isError = state.usernameIsError,
            onValueChange = {
                component.changeUsernameError(it.length > 20 || it.length < 2)
                component.changeUsername(it)
            },
            supportingText = if (state.usernameIsError) { { Texts.Error(stringResource(Res.string.username_error))} } else null,
            placeholderText = stringResource(Res.string.username)
        )

        TextFields.OutlinedTextField(
            value = state.email,
            isError = state.emailIsError,
            onValueChange = {
                component.changeEmailError(!it.validEmail())
                component.changeEmail(it)
            },
            supportingText = if (state.emailIsError) { { Texts.Error(stringResource(Res.string.email_error))} } else null,
            placeholderText = stringResource(Res.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        TextFields.OutlinedTextField(
            value = state.password,
            isError = state.passwordIsError,
            onValueChange = {
                component.changePasswordError(it.length > 24 || it.length < 4)
                component.changeConfirmPasswordError(it != state.confirmPassword)
                component.changePassword(it)
            },
            visualTransformation = if (state.isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = if (state.passwordIsError) { { Texts.Error(stringResource(Res.string.password_error))} } else null,
            placeholderText = stringResource(Res.string.password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            trailingIcon = {
                IconButton(
                    onClick = {component.changeVisiblePassword(!state.isVisiblePassword)}
                ){ Icon(
                    painterResource(if(state.isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )

        TextFields.OutlinedTextField(
            value = state.confirmPassword,
            isError = state.confirmPasswordIsError,
            onValueChange = {
                component.changeConfirmPasswordError(it != state.password)
                component.changeConfirmPassword(it)
            },
            visualTransformation = if (state.isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = if (state.confirmPasswordIsError) { { Texts.Error(stringResource(Res.string.confirm_password_error))} } else null,
            placeholderText = stringResource(Res.string.confirm_password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(
                    onClick = {component.changeVisiblePassword(!state.isVisiblePassword)}
                ){ Icon(
                    painterResource(if(state.isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )
    }
}