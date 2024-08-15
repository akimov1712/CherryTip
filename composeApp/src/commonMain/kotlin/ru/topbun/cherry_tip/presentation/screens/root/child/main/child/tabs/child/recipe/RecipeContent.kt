package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.components.CustomTabRow
import ru.topbun.cherry_tip.presentation.ui.components.TextFields

@Composable
fun RecipeScreen(
    component: RecipeComponent,
    modifier: Modifier = Modifier.statusBarsPadding()
) {
   Column(
       modifier = modifier
           .fillMaxSize()
           .padding(horizontal = 20.dp, vertical = 20.dp)
   ) {
        val state by component.state.collectAsState()
        TextFields.Search(
            value = state.query,
            onValueChange = { if(it.length <= 40) component.changeQuery(it) },
        )
       Spacer(Modifier.height(16.dp))
       CustomTabRow(
           selectedIndex = state.selectedIndex,
           items = state.tabs.map { stringResource(it.titleRes) }
       ){
           component.changeTab(it)
       }
       Spacer(Modifier.height(16.dp))
   }
}