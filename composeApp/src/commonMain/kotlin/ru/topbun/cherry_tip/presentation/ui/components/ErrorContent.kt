package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_error_warning
import cherrytip.composeapp.generated.resources.oops_error
import cherrytip.composeapp.generated.resources.try_again
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorModal(
    text: String,
    onDismissDialog: () -> Unit = {},
    onClick: () -> Unit
) {
    DialogWrapper(
        onDismissDialog = onDismissDialog
    ){
        ErrorContent(text = text, onClick = onClick)
    }
}

@Composable
fun ErrorContent(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_error_warning),
            contentDescription = null
        )
        Spacer(Modifier.height(20.dp))
        Texts.Title(
            text = stringResource(Res.string.oops_error),
            fontSize = 20.sp
        )
        Spacer(Modifier.height(16.dp))
        Texts.General(
            modifier = Modifier.defaultMinSize(minHeight = 80.dp),
            text = text
        )
        Spacer(Modifier.height(20.dp))
        Buttons.Purple(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = onClick
        ){
            Texts.Button(
                text = stringResource(Res.string.try_again),
                fontSize = 16.sp
            )
        }
    }
}