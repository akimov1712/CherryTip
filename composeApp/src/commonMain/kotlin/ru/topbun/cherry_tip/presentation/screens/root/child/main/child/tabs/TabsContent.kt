package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeScreen
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.ProfileScreen
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun TabsScreen(
    component: TabsComponent,
    modifier: Modifier = Modifier.background(Colors.White).statusBarsPadding()
) {
    val stack by component.stack.subscribeAsState()
    val activeInstance by derivedStateOf { stack.active.configuration }
    val tabs = listOf(
        TabsNavItems.Home{component.openHome()},
        TabsNavItems.Calendar{component.openCalendar()},
        TabsNavItems.Recipes{component.openRecipe()},
        TabsNavItems.Settings{component.openSettings()}
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Colors.White,
        bottomBar = {
            BottomAppBar(
                containerColor = Colors.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.White)
                    .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.5.dp, Colors.PurpleBackground, RoundedCornerShape(24.dp))
                    .padding(vertical = 4.dp)
            ) {
                tabs.forEachIndexed { index, item ->
                    println(activeInstance)
                    val isSelected = activeInstance == item.config
                    println(isSelected)
                    NavigationItem(
                        isSelected = isSelected,
                        onClick = { item.onClickItem() },
                        icon = painterResource(item.iconRes),
                        label = stringResource(item.titleRes)
                    )
                }
            }
        }
    ){
        Children(
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            stack = component.stack,
            animation = stackAnimation { child ->
                fade()
            }
        ){
            when(val instance = it.instance){
                is TabsComponent.Child.Home -> HomeScreen(instance.component, modifier)
                is TabsComponent.Child.Calendar -> CalendarScreen(instance.component, modifier)
                is TabsComponent.Child.Settings -> ProfileScreen(instance.component, modifier)
                is TabsComponent.Child.Recipe -> RecipeScreen(instance.component, modifier)
            }
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: Painter,
    label: String
) {
    val color = if (isSelected) Colors.Purple else Colors.DarkGray
    Column(
        modifier = Modifier.weight(1f).clickable(
            interactionSource = MutableInteractionSource(),
            indication = null
        ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color
        )
        Texts.Option(
            text = label,
            fontSize = 14.sp,
            color = color
        )
    }
}

