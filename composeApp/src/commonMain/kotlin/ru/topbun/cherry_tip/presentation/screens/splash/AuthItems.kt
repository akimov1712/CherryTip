package ru.topbun.cherry_tip.presentation.screens.splash

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_apple
import cherrytip.composeapp.generated.resources.ic_email
import cherrytip.composeapp.generated.resources.ic_facebook
import cherrytip.composeapp.generated.resources.ic_google
import cherrytip.composeapp.generated.resources.sign_apple
import cherrytip.composeapp.generated.resources.sign_email
import cherrytip.composeapp.generated.resources.sign_facebook
import cherrytip.composeapp.generated.resources.sign_google
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class AuthItems(
    val textRes: StringResource,
    val iconRes: DrawableResource
) {

    data object Apple: AuthItems(textRes = Res.string.sign_apple, iconRes = Res.drawable.ic_apple)
    data object Facebook: AuthItems(textRes = Res.string.sign_facebook, iconRes = Res.drawable.ic_facebook)
    data object Google: AuthItems(textRes = Res.string.sign_google, iconRes = Res.drawable.ic_google)
    data object Email: AuthItems(textRes = Res.string.sign_email, iconRes = Res.drawable.ic_email)

}