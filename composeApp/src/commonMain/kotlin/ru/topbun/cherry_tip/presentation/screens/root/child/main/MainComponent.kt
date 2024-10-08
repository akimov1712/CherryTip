package ru.topbun.cherry_tip.presentation.screens.root.child.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal.AppendMealComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.tipsDetail.Tips
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsComponent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponent

interface MainComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Tabs(val component: TabsComponent): Child
        data class Challenge(val component: ChallengeComponent): Child
        data class ChallengeDetail(val component: ChallengeDetailComponent): Child
        data class Account(val component: AccountComponent): Child
        data class Profile(val component: ProfileComponent): Child
        data class Goal(val component: GoalComponent): Child
        data class Units(val component: UnitsComponent): Child
        data class AddRecipe(val component: AddRecipeComponent): Child
        data class DetailIngest(val component: DetailIngestComponent): Child
        data class AppendMeal(val component: AppendMealComponent): Child
    }


}