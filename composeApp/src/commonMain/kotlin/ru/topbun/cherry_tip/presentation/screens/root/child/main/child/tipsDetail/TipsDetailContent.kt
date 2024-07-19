package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.img_tips_sweet
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsDetailScreen(modifier: Modifier = Modifier) {
    val state = SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Expanded
    )
    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        sheetState = state,
        containerColor = Colors.Transparent,
        onDismissRequest = {},
        scrimColor = Color(17, 17, 17).copy(0.7f),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.size(50.dp, 6.dp).background(Colors.White, CircleShape))
            Spacer(Modifier.height(6.dp))
            Column(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(241, 126, 190),
                                Color(248, 151, 210),
                            )
                        ), RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))
                Texts.Title("Cut down fat & sugar", color = Color(185, 0, 110))
                Spacer(Modifier.height(10.dp))
                Texts.General(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = "Cutting sugar out of your diet is one strategy to lose weight and feel healthier, but it can be a tough transition. Here are eight tips on how to go sugar-free without going crazy, plus ideas on what to eat on a low-sugar or no-sugar diet.",
                    color = Color(185, 0, 110)
                )
                Spacer(Modifier.height(10.dp))
                Image(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    painter = painterResource(Res.drawable.img_tips_sweet),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
        }
    }
}
