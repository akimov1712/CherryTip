package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldCharSequence
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.add_recipe
import cherrytip.composeapp.generated.resources.challenges
import cherrytip.composeapp.generated.resources.details
import cherrytip.composeapp.generated.resources.ic_send_image
import coil3.compose.AsyncImagePainter.State.Empty.painter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Buttons.BackWithTitle
import ru.topbun.cherry_tip.presentation.ui.components.TextFields
import ru.topbun.cherry_tip.presentation.ui.components.Texts

@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier.statusBarsPadding()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        BackWithTitle(stringResource(Res.string.add_recipe)) {  }
        Spacer(Modifier.height(30.dp))
        Texts.Option(
            text = stringResource(Res.string.details),
            color = Colors.Black
        )
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonChoiceImage()

        }

    }
}

@Composable
private fun ButtonChoiceImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Colors.White)
            .border(2.dp, Colors.PurpleBackground, RoundedCornerShape(16.dp))
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(Res.drawable.ic_send_image),
            contentDescription = null,
            tint = Colors.Purple
        )
    }
}