package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_important
import org.jetbrains.compose.resources.painterResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun NumericalTextField(
    title: String,
    placeholderText: String,
    value: String,
    isImportant: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                Texts.Option(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.Gray,
                    fontSize = 16.sp
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier.size(6.dp).align(Alignment.Top),
                        painter = painterResource(Res.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.Red
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            TextFields.OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = value,
                onValueChange = onValueChange,
                placeholderText = placeholderText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
            )
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}