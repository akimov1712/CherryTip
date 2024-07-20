package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tipsDetail

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.ic_tips_active
import cherrytip.composeapp.generated.resources.ic_tips_fish
import cherrytip.composeapp.generated.resources.ic_tips_fruits
import cherrytip.composeapp.generated.resources.ic_tips_sugar
import cherrytip.composeapp.generated.resources.ic_tips_water
import cherrytip.composeapp.generated.resources.img_tips_active
import cherrytip.composeapp.generated.resources.img_tips_fish
import cherrytip.composeapp.generated.resources.img_tips_fruits
import cherrytip.composeapp.generated.resources.img_tips_sugar
import cherrytip.composeapp.generated.resources.img_tips_water
import cherrytip.composeapp.generated.resources.tips_active_descr
import cherrytip.composeapp.generated.resources.tips_active_title
import cherrytip.composeapp.generated.resources.tips_fish_descr
import cherrytip.composeapp.generated.resources.tips_fish_title
import cherrytip.composeapp.generated.resources.tips_fruits_descr
import cherrytip.composeapp.generated.resources.tips_fruits_title
import cherrytip.composeapp.generated.resources.tips_sugar_descr
import cherrytip.composeapp.generated.resources.tips_sugar_title
import cherrytip.composeapp.generated.resources.tips_water_descr
import cherrytip.composeapp.generated.resources.tips_water_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class Tips(
    val title: StringResource,
    val descr: StringResource,
    val icon: DrawableResource,
    val image: DrawableResource,
    val textColor: Color,
    val background: Brush,
) {

    FRUITS(
        title = Res.string.tips_fruits_title,
        descr = Res.string.tips_fruits_descr,
        icon = Res.drawable.ic_tips_fruits,
        image = Res.drawable.img_tips_fruits,
        textColor = Color.White,
        background = Brush.horizontalGradient(
            listOf(Color(243, 60, 54), Color(249, 220, 29))
        )
    ),

    WATER(
        title = Res.string.tips_water_title,
        descr = Res.string.tips_water_descr,
        icon = Res.drawable.ic_tips_water,
        image = Res.drawable.img_tips_water,
        textColor = Color(50, 138, 209),
        background = Brush.horizontalGradient(
            listOf(Color(213, 243, 254), Color(213, 243, 254))
        )
    ),

    SUGAR(
        title = Res.string.tips_sugar_title,
        descr = Res.string.tips_sugar_descr,
        icon = Res.drawable.ic_tips_sugar,
        image = Res.drawable.img_tips_sugar,
        textColor = Color(185, 0, 110),
        background = Brush.horizontalGradient(
            listOf(Color(241, 126, 190), Color(248, 151, 210))
        )
    ),

    ACTIVE(
        title = Res.string.tips_active_title,
        descr = Res.string.tips_active_descr,
        icon = Res.drawable.ic_tips_active,
        image = Res.drawable.img_tips_active,
        textColor = Color.White,
        background = Brush.verticalGradient(
            listOf(Color(0xffFFBA22), Color(0xffFF52AF))
        )
    ),

    FISH(
        title = Res.string.tips_fish_title,
        descr = Res.string.tips_fish_descr,
        icon = Res.drawable.ic_tips_fish,
        image = Res.drawable.img_tips_fish,
        textColor = Color(0xff8E41D5),
        background = Brush.verticalGradient(
            listOf(Color(0xffFEEAA1), Color(0xffFEEAA1))
        )
    ),

}