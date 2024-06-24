package ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.height

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.what_your_height
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.auth.survey.fragments.FragmentsComponents

@Composable
fun HeightFragmentContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    FragmentsComponents.FragmentWrapper(
        modifier = modifier,
        title = stringResource(Res.string.what_your_height)
    ){

    }
}