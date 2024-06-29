package ru.topbun.cherry_tip.presentation.screens.auth.signUp

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
import androidx.compose.runtime.Composable
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
import cherrytip.composeapp.generated.resources.confirm_password
import cherrytip.composeapp.generated.resources.confirm_password_error
import cherrytip.composeapp.generated.resources.email
import cherrytip.composeapp.generated.resources.email_error
import cherrytip.composeapp.generated.resources.have_account
import cherrytip.composeapp.generated.resources.ic_apple
import cherrytip.composeapp.generated.resources.ic_apple_fruit
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
import ru.topbun.cherry_tip.domain.entity.Login
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.utills.validEmail


@Composable
fun SignUpContent(modifier: Modifier = Modifier.statusBarsPadding()) {
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
        ) {}
        Spacer(Modifier.height(40.dp))
        Texts.Title(
            stringResource(Res.string.sign_up_descr),
            fontSize = 30.sp,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(40.dp))
        val (login, isValid) = LoginFields()
        Spacer(Modifier.height(40.dp))
        ButtonSignUp(isValid){}
        Spacer(Modifier.height(40.dp))
        SeparateText{}
        Spacer(Modifier.height(20.dp))
        AuthMethods()
        Spacer(Modifier.weight(1f))
        TextHaveAccount{}
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun TextHaveAccount(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Texts.General(stringResource(Res.string.have_account))
        Texts.Link(stringResource(Res.string.login),
            modifier = Modifier.clickable(interactionSource = MutableInteractionSource(), indication = null, onClick = onClick))
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
private fun SeparateText(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.fillMaxWidth().height(2.dp).background(Colors.PurpleBackground, CircleShape))
        Texts.General(
            modifier = Modifier.clickable(indication = null, interactionSource = MutableInteractionSource()){
                onClick() }.background(Colors.White).padding(horizontal = 10.dp),
            text = stringResource(Res.string.or_login),
        )
    }
}

@Composable
private fun ButtonSignUp(isValid: Boolean, onClick: () -> Unit) {
    Buttons.Purple(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        onClick = onClick,
        enabled = isValid
    ) { Texts.Button(stringResource(Res.string.sign_up)) }
}

@Composable
fun LoginFields(): Pair<Login, Boolean> {
    var username by rememberSaveable { mutableStateOf("") }
    var usernameError by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPasswordError by rememberSaveable { mutableStateOf(false) }
    var isVisiblePassword by rememberSaveable{ mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextFields.OutlinedTextField(
            value = username,
            isError = usernameError,
            onValueChange = {
                usernameError = it.length > 20 || it.length < 2
                username = it
            },
            supportingText = { if (usernameError) Texts.Error(stringResource(Res.string.username_error)) },
            placeholderText = stringResource(Res.string.username)
        )

        TextFields.OutlinedTextField(
            value = email,
            isError = emailError,
            onValueChange = {
                emailError = !it.validEmail()
                email = it
            },
            supportingText = { if (emailError) Texts.Error(stringResource(Res.string.email_error)) },
            placeholderText = stringResource(Res.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        TextFields.OutlinedTextField(
            value = password,
            isError = passwordError,
            onValueChange = {
                passwordError = it.length > 24 || it.length < 4
                confirmPasswordError = it != confirmPassword
                password = it
            },
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = { if (passwordError) Texts.Error(stringResource(Res.string.password_error)) },
            placeholderText = stringResource(Res.string.password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
            trailingIcon = {
                IconButton(
                    onClick = {isVisiblePassword = !isVisiblePassword}
                ){ Icon(
                    painterResource(if(isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )

        TextFields.OutlinedTextField(
            value = confirmPassword,
            isError = confirmPasswordError,
            onValueChange = {
                confirmPasswordError = it != password
                confirmPassword = it
            },
            visualTransformation = if (isVisiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            supportingText = { if (confirmPasswordError) Texts.Error(stringResource(Res.string.confirm_password_error)) },
            placeholderText = stringResource(Res.string.confirm_password),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            trailingIcon = {
                IconButton(
                    onClick = {isVisiblePassword = !isVisiblePassword}
                ){ Icon(
                    painterResource(if(isVisiblePassword) Res.drawable.ic_show else Res.drawable.ic_hide),
                    contentDescription = null,
                    tint = Colors.Gray
                ) }
            }
        )
    }

    val isValid =
        !usernameError &&
                !emailError &&
                !passwordError &&
                !confirmPasswordError &&
                username.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank()
    val login = Login(username, email, password)
    return login to isValid
}