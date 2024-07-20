package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsDetailScreen(
    tips: Tips,
    state: SheetState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier.fillMaxSize().padding(top = 64.dp),
        sheetState = state,
        containerColor = Colors.Transparent,
        onDismissRequest = onDismiss,
        scrimColor = Color(17, 17, 17).copy(0.7f),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        windowInsets = WindowInsets(0),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Box(Modifier.size(50.dp, 7.dp).background(Colors.White, CircleShape))
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .background(tips.background, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Texts.Title(stringResource(tips.title), color = tips.textColor)
                Spacer(Modifier.height(10.dp))
                Texts.General(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(tips.descr),
                    color = tips.textColor
                )
                Spacer(Modifier.height(10.dp))
                Image(
                    modifier = Modifier.weight(1f).fillMaxWidth().padding(bottom = 64.dp),
                    painter = painterResource(tips.image),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.BottomCenter,
                    contentDescription = null
                )
            }
        }
    }
}
