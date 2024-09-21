package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.tipsDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.AppModalBottomSheet
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsDetailScreen(
    tips: Tips,
    state: SheetState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    AppModalBottomSheet(
        modifier = modifier.padding(top = 64.dp),
        state = state,
        onDismiss = onDismiss
    ){
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