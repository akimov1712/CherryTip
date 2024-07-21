package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.error
import cherrytip.composeapp.generated.resources.ic_glass_add
import cherrytip.composeapp.generated.resources.ic_glass_empty
import cherrytip.composeapp.generated.resources.ic_glass_fill
import cherrytip.composeapp.generated.resources.ic_info
import cherrytip.composeapp.generated.resources.loading
import cherrytip.composeapp.generated.resources.remember_hydrated
import cherrytip.composeapp.generated.resources.water_consumption
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.Glass
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStore
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Glass(component: HomeComponent, onClickAdd: () -> Unit) {
    val scope = rememberCoroutineScope()
    val state by component.state.collectAsState()
    GlassTitle(state.glassStateStatus)

    Spacer(Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(Colors.Purple, RoundedCornerShape(20.dp))
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(val stateScreen = state.glassStateStatus){
            HomeStore.State.GlassStateStatus.Loading -> CircularProgressIndicator(color = Colors.White)
            is HomeStore.State.GlassStateStatus.Result -> {
                val lazyListState = rememberLazyListState(stateScreen.state.firstPageIndex)
                LaunchedEffect(stateScreen.state.firstPageIndex){ lazyListState.animateScrollToItem(stateScreen.state.firstPageIndex) }
                LazyRow(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxWidth(),
                    flingBehavior = rememberSnapFlingBehavior(lazyListState)
                ) {
                    items(items = stateScreen.state.consumption.chunked(5)) {
                        pageGlasses(it, onClickAdd)
                    }
                }
                IndicatorPages(
                    countPages = stateScreen.state.countPages,
                    indexSelected = lazyListState.firstVisibleItemIndex,
                ){
                    scope.launch { lazyListState.animateScrollToItem(it) }
                }
            }
            else -> Unit
        }

        WarningDontForgetDrink()
    }
}

@Composable
private fun WarningDontForgetDrink() {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .background(Colors.PurpleTransparent, RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_info),
            contentDescription = null,
            tint = Colors.White
        )
        Spacer(Modifier.width(10.dp))
        Texts.Option(
            text = stringResource(Res.string.remember_hydrated),
            color = Colors.White,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun GlassTitle(state: HomeStore.State.GlassStateStatus) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Texts.Title(stringResource(Res.string.water_consumption), textAlign = TextAlign.Start)
        Spacer(Modifier.weight(1f))
        var titleText = when(state){
            HomeStore.State.GlassStateStatus.Error -> stringResource(Res.string.error)
            HomeStore.State.GlassStateStatus.Loading -> stringResource(Res.string.loading)
            is HomeStore.State.GlassStateStatus.Result -> "${state.state.mlConsumed} / ${state.state.mlTotal}"
            else -> ""
        }
        Texts.Option(titleText, fontSize = 16.sp, color = Colors.Black)
    }
}

@Composable
private fun IndicatorPages(
    countPages: Int,
    indexSelected: Int,
    onClickScroll: (position: Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        repeat(countPages) {
            val background = if (it == indexSelected) Colors.White else Colors.White.copy(0.3f)
            Box(
                Modifier
                    .size(10.dp)
                    .background(background, CircleShape)
                    .clickable{ onClickScroll(it) }
            )

        }
    }
}

@Composable
private fun LazyItemScope.pageGlasses(glasses: List<Glass>, onClickAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        glasses.forEach {
            val icon = when(it.type){
                Glass.Type.EMPTY -> Res.drawable.ic_glass_empty
                Glass.Type.ADDED -> Res.drawable.ic_glass_add
                Glass.Type.FILL -> Res.drawable.ic_glass_fill
            }
            Image(
                painter = painterResource(icon),
                modifier = Modifier.then(if (it.type == Glass.Type.ADDED)
                    Modifier.clickable(
                        onClick = onClickAdd,
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) else Modifier
                ),
                contentDescription = null
            )
        }
    }
}