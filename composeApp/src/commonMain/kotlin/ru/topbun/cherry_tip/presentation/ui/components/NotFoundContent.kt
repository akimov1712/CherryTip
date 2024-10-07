package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_error_warning
import cherrytip.composeapp.generated.resources.ic_result_nof_found
import cherrytip.composeapp.generated.resources.no_result_found
import cherrytip.composeapp.generated.resources.oops_error
import cherrytip.composeapp.generated.resources.try_again
import cherrytip.composeapp.generated.resources.we_cant_find
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NotFoundContent(
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_result_nof_found),
            contentDescription = null
        )
        Spacer(Modifier.height(20.dp))
        Texts.Title(
            text = stringResource(Res.string.no_result_found),
            fontSize = 20.sp
        )
        Spacer(Modifier.height(16.dp))
        Texts.General(
            modifier = Modifier.width(280.dp),
            text = stringResource(Res.string.we_cant_find),
        )
    }
}