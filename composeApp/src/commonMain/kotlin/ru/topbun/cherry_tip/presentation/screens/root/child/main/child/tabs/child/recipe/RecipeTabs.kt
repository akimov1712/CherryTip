package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe

import cherrytip.composeapp.generated.resources.Res
import cherrytip.composeapp.generated.resources.my_recipes
import cherrytip.composeapp.generated.resources.recipes
import org.jetbrains.compose.resources.StringResource

enum class RecipeTabs(val titleRes: StringResource) {
    Recipes(Res.string.recipes), MyRecipes(Res.string.my_recipes)
}