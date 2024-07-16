package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.ic_back
import cherrytip.composeapp.generated.resources.ic_clock
import cherrytip.composeapp.generated.resources.ic_glass_add
import cherrytip.composeapp.generated.resources.ic_glass_empty
import cherrytip.composeapp.generated.resources.ic_glass_fill
import cherrytip.composeapp.generated.resources.ic_info
import cherrytip.composeapp.generated.resources.ic_lightning
import cherrytip.composeapp.generated.resources.ic_peach
import cherrytip.composeapp.generated.resources.img_test
import cherrytip.composeapp.generated.resources.more
import cherrytip.composeapp.generated.resources.nutrition_tips
import cherrytip.composeapp.generated.resources.remember_hydrated
import cherrytip.composeapp.generated.resources.see_all
import cherrytip.composeapp.generated.resources.water_consumption
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.glass.GlassEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun HomeContent(
    component: HomeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    Column(
        modifier = modifier
            .padding(vertical = 24.dp),
    ) {
        Tips()
        Spacer(Modifier.height(20.dp))
        Challenge()
        Spacer(Modifier.height(20.dp))
        Glass(component){ component.addDrinkGlass() }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Glass(component: HomeComponent, onClickAdd: () -> Unit) {
    val scope = rememberCoroutineScope()
    val state by component.state.collectAsState()
    GlassTitle(state)
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
        val lazyListState = rememberLazyListState(state.firstPageIndex)
        LazyRow(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth(),
            flingBehavior = rememberSnapFlingBehavior(lazyListState)
        ) {
            items(items = state.consumption.chunked(5)) {
                pageGlasses(it, onClickAdd)
            }
        }
        IndicatorPages(
            countPages = state.countPages,
            indexSelected = lazyListState.firstVisibleItemIndex,
        ){
            scope.launch { lazyListState.animateScrollToItem(it) }
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
private fun GlassTitle(state: HomeStore.State) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Texts.Title(stringResource(Res.string.water_consumption), textAlign = TextAlign.Start)
        Spacer(Modifier.weight(1f))
        Texts.Option("${state.mlConsumed} / ${state.mlTotal}", fontSize = 16.sp, color = Colors.Black)
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

@Composable
private fun Challenge() {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Texts.Title(stringResource(Res.string.challenges), textAlign = TextAlign.Start)
        Spacer(Modifier.weight(1f))
        Texts.Option(stringResource(Res.string.see_all), fontSize = 16.sp)
    }
    Spacer(Modifier.height(16.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        repeat(10) {
            item { ChallengeItem { } }
        }
    }
}

@Composable
private fun Tips() {
    Texts.Title(
        stringResource(Res.string.nutrition_tips),
        modifier = Modifier.padding(start = 20.dp),
        textAlign = TextAlign.Start
    )
    Spacer(Modifier.height(16.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        repeat(10) {
            item { TipsButton() }
        }
    }
}

@Composable
private fun ChallengeItem(onClickMore: () -> Unit) {
    Card(
        modifier = Modifier.width(310.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFB7CE))
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            InfoChallenge(onClickMore)
            Image(
                modifier = Modifier.fillMaxWidth().offset(y = 50.dp, x = 10.dp).scale(1.2f),
                painter = painterResource(Res.drawable.img_test),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun InfoChallenge(onClickMore: () -> Unit) {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Texts.Option("Sweet Free", color = Colors.Black)
        IconWithText(painter = painterResource(Res.drawable.ic_clock), text = "7 days")
        IconWithText(painter = painterResource(Res.drawable.ic_lightning), text = "Medium")
        Buttons.Button(
            onClick = onClickMore,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Colors.White),
            contentPadding = PaddingValues(15.dp, 10.dp),
        ) {
            Texts.Button(
                stringResource(Res.string.more),
                color = Colors.Black,
                fontSize = 14.sp
            )
            Spacer(Modifier.width(7.dp))
            Icon(
                modifier = Modifier.rotate(180f).size(16.dp),
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = null,
                tint = Colors.Black
            )
        }
    }
}

@Composable
private fun IconWithText(
    painter: Painter,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painter, contentDescription = null, tint = Colors.GrayDark)
        Spacer(Modifier.width(7.dp))
        Texts.General(text, fontSize = 14.sp, color = Colors.GrayDark)
    }
}

@Composable
private fun TipsButton() {
    Column {
        Card(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Colors.PurpleBackground),
            colors = CardDefaults.cardColors(containerColor = Colors.Transparent),
            onClick = {}
        ){
            Icon(modifier = Modifier.padding(20.dp), painter = painterResource(Res.drawable.ic_peach), contentDescription = null)
        }
        Spacer(Modifier.height(7.dp))
        Texts.Tips("Fruits &\n" +
                "Vegetables")
    }
}