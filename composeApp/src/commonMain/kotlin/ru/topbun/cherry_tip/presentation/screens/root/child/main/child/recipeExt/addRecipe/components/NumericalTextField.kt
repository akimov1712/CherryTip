package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import cherrytip.composeapp.generated.resources.recipe_add_error_name
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun NumericalTextField(
    title: String,
    placeholderText: String,
    value: String,
    isLoading: Boolean,
    isImportant: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Number,
    supportingText: @Composable (() -> Unit)? = null,
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
                supportingText = supportingText,
                onValueChange = onValueChange,
                enabled = !isLoading,
                placeholderText = placeholderText,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Go
                ),
            )
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}
@Composable
fun NumericalText(
    title: String,
    value: String,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Texts.Option(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = title,
                color = Colors.Black,
                fontSize = 16.sp
            )
            Spacer(Modifier.width(20.dp))
            Text(
                modifier = Modifier.weight(1f).padding(start = 16.dp),
                text = value,
                style = TextFields.textStyle.copy(color = Colors.Black)
            )
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}