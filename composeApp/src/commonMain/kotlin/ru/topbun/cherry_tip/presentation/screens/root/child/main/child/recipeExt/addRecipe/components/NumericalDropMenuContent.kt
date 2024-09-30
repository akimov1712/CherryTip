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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_important
import cherrytip.composeapp.generated.resources.not_selected
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts
import ru.topbun.cherry_tip.presentation.ui.utills.localWidth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> NumericalDropMenuContent(
    title: String,
    items: List<T>,
    selectedItem: T,
    isImportant: Boolean = false,
    onValueChange: (T) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
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
            var isDropMenuVisible by rememberSaveable { mutableStateOf(false) }
            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .weight(1f),
                expanded = isDropMenuVisible,
                onExpandedChange = { isDropMenuVisible = !isDropMenuVisible }
            ) {
                TextFields.OutlinedDropDownMenu(
                    modifier = Modifier
                        .menuAnchor()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    value = selectedItem?.toString() ?: "",
                    placeholderText = stringResource(Res.string.not_selected),
                    isOpen = isDropMenuVisible
                )
                DropMenuDifficulty(
                    modifier = Modifier.localWidth(textFieldSize.width),
                    isDropMenuVisible = isDropMenuVisible,
                    items = items,
                    onValueChange = onValueChange,
                    onDismissRequest = { isDropMenuVisible = false }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}

@Composable
private fun <T> DropMenuDifficulty(
    modifier: Modifier = Modifier,
    isDropMenuVisible: Boolean,
    items: List<T>,
    onValueChange: (T) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier.background(Colors.White),
        expanded = isDropMenuVisible,
        onDismissRequest = { onDismissRequest() },
    ) {
        items.forEach {
            DropdownMenuItem(
                text = {
                    Texts.Option(
                        text = it?.toString()
                            ?: stringResource(Res.string.not_selected),
                        color = Colors.DarkGray
                    )
                },
                onClick = { onValueChange(it); onDismissRequest() }
            )
        }
    }
}