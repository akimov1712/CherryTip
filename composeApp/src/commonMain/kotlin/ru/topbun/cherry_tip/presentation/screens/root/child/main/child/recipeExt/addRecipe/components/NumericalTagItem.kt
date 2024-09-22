package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_important
import cherrytip.composeapp.generated.resources.not_selected
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.topbun.cherry_tip.domain.entity.recipe.TagEntity
import ru.topbun.cherry_tip.presentation.ui.Colors
import ru.topbun.cherry_tip.presentation.ui.components.Texts


@Composable
fun NumericalTagItem(
    title: String,
    tag: TagEntity?,
    isImportant: Boolean = false,
    onClickChangeTag: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Texts.Option(
                    textAlign = TextAlign.Start,
                    text = title,
                    color = Colors.Gray,
                    fontSize = 16.sp
                )
                if (isImportant) {
                    Icon(
                        modifier = Modifier.size(6.dp).align(Alignment.Top),
                        painter = painterResource(Res.drawable.ic_important),
                        contentDescription = null,
                        tint = Colors.Red
                    )
                }
            }
            Spacer(Modifier.width(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .clickable { onClickChangeTag() }
                    .border(1.dp, Colors.PurpleBackground, RoundedCornerShape(13.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                tag?.let {
                    AsyncImage(
                        modifier = Modifier.size(12.dp),
                        model = it.icon,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Texts.Button(
                        text = title,
                        color = Colors.Black
                    )
                } ?: Texts.Button(
                    text = stringResource(Res.string.not_selected),
                    color = Colors.Gray
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth().height(1.dp).background(Colors.PurpleBackground))
    }
}