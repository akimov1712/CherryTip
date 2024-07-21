package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeScreen

@Composable
fun TabsScreen(
    componentContext: ComponentContext,
    modifier: Modifier = Modifier
) {
    val component = getKoin().get<HomeComponentImpl> { parametersOf(componentContext) }
    HomeScreen(component)
}