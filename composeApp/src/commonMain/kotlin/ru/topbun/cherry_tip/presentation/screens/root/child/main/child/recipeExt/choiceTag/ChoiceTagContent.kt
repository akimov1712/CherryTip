package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.choiceTag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.AppModalBottomSheet
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceTagModal(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val sheetState = SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Hidden
    )
    AppModalBottomSheet(
        modifier = modifier,
        state = sheetState,
        onDismiss = { scope.launch { sheetState.hide() } }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.White, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .padding(start = 20.dp, top = 24.dp, end = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Texts.Option(
                    text = stringResource(Res.string.meals),
                    color = Colors.Black
                )
                Spacer(Modifier.height(10.dp))
                Row {
                    // item
                   Row(
                       modifier = Modifier.background(Colors.Purple, CircleShape).padding(12.dp)
                   ){
                       AsyncImage(
                           modifier = Modifier.size(24.dp),
                           model = "",
                           contentDescription = null
                       )
                       Spacer(Modifier.width(7.dp))
                       Texts.Option(
                           text = "Breakfast",
                           color = Colors.White,
                           fontSize = 16.sp
                       )
                   }
                }
            }
        }
    }
}