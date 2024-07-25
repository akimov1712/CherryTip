package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements.Challenge
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements.Glass
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements.Tips
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail.Tips
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail.TipsDetailScreen

const val COUNT_GLASS_PAGE = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    component: HomeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val state = SheetState(
        skipPartiallyExpanded = true,
        density = LocalDensity.current,
        initialValue = SheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    var tipsSelected by remember{ mutableStateOf<Tips?>(null) }
    Column(
        modifier = modifier
            .padding(vertical = 24.dp),
    ) {
        Tips{
            tipsSelected = it
            scope.launch { state.expand() }
        }
        Spacer(Modifier.height(20.dp))
        Challenge(onClickAll = { component.openChallenge() }, onClickMore = { component.openChallengeDetail() } )
        Spacer(Modifier.height(20.dp))
        Glass(component){ component.addDrinkGlass() }
    }
    tipsSelected?.let {
        TipsDetailScreen(it, state){ tipsSelected = null}
    }
}

