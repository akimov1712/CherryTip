package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.nutrition_tips
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail.Tips
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun Tips(onClickTips: (tips: Tips) -> Unit) {
    Texts.Title(
        stringResource(Res.string.nutrition_tips),
        modifier = Modifier.padding(start = 20.dp),
        textAlign = TextAlign.Start
    )
    Spacer(Modifier.height(16.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),

        ) {
        items(items = Tips.entries.toList()) {
            TipsButton(it){ onClickTips(it) }
        }
    }
}



@Composable
private fun TipsButton(tips: Tips, onClickTips: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Colors.PurpleBackground),
            colors = CardDefaults.cardColors(containerColor = Colors.Transparent),
            onClick = onClickTips
        ){
            Icon(modifier = Modifier.padding(20.dp), painter = painterResource(tips.icon), contentDescription = stringResource(tips.title))
        }
        Spacer(Modifier.height(7.dp))
        Texts.Tips(stringResource(tips.title), modifier = Modifier.sizeIn(maxWidth = 80.dp), maxLines = 2)
    }
}