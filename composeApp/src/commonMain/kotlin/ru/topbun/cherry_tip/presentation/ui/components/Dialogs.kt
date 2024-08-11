package ru.topbun.cherry_tip.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.apply
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors

@Composable
fun DialogWrapper(
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(color = Colors.White, RoundedCornerShape(24.dp))
                .padding(horizontal = 12.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            content = content
        )
    }
}
