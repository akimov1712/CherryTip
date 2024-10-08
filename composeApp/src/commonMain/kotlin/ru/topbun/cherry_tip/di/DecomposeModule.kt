package ru.topbun.cherry_tip.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import kotlinx.datetime.LocalDate
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash.SplashComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash.SplashStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal.AppendMealComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.appendMeal.AppendMealStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest.DetailIngestStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challengeDetail.ChallengeDetailStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.recipeExt.addRecipe.AddRecipeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.goal.GoalStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units.UnitsStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.calendar.CalendarStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.recipe.RecipeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings.SettingsStoreFactory

val decomposeModule = module {
    single<StoreFactory>{ DefaultStoreFactory() }
    splashModule()
    authModule()
    loginModule()
    signUpModule()
    reminderModule()
    surveyModule()
    mainModule()
    tabsModule()
    homeModule()
    settingsModule()
    challengeModule()
    challengeDetailModule()
    accountModule()
    profileModule()
    goalModule()
    unitsModule()
    recipeModule()
    addRecipeModule()
    calendarModule()
    detailIngestModule()
    appendMealModule()
}

private fun Module.appendMealModule(){
    factory<AppendMealStoreFactory> { AppendMealStoreFactory(get(), get(), get(), get(),get(),get(),get(),get()) }
    factory { params ->
        val componentContext = params.get<ComponentContext>()
        val date = params.get<LocalDate>()
        val calendarType = params.get<CalendarType>()
        val onClickAddRecipe = params.get<() -> Unit>()
        val onClickBack = params.get<() -> Unit>()
        val onLogOut = params.get<() -> Unit>()

        AppendMealComponentImpl(
            componentContext = componentContext,
            date = date,
            calendarType = calendarType,
            onClickAddRecipe = onClickAddRecipe,
            onClickBack = onClickBack,
            onLogOut = onLogOut,
            storeFactory = get(),
        )
    }
}

private fun Module.detailIngestModule(){
    factory<DetailIngestStoreFactory> { DetailIngestStoreFactory(get(), get(), get(), get()) }
    factory { params ->
        val componentContext = params.get<ComponentContext>()
        val date = params.get<LocalDate>()
        val calendarType = params.get<CalendarType>()
        val onClickBack = params.get<() -> Unit>()
        val onClickAddMeal = params.get<() -> Unit>()
        val onOpenAuth = params.get<() -> Unit>()

        DetailIngestComponentImpl(
            componentContext = componentContext,
            date = date,
            calendarType = calendarType,
            onClickBack = onClickBack,
            onClickAddMeal = onClickAddMeal,
            onOpenAuth = onOpenAuth,
            storeFactory = get(),
        )
    }
}

private fun Module.calendarModule(){
    factory<CalendarStoreFactory> { CalendarStoreFactory(get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onClickAppendMeal: (LocalDate, CalendarType) -> Unit,
        onClickDetailIngest: (LocalDate, CalendarType) -> Unit,
        onOpenAuth: () -> Unit,
    ) ->
        CalendarComponentImpl(
            componentContext = componentContext,
            onClickAppendMeal = onClickAppendMeal,
            onClickDetailIngest = onClickDetailIngest,
            onOpenAuth = onOpenAuth,
            storeFactory = get(),
        )
    }
}

private fun Module.addRecipeModule(){
    factory<AddRecipeStoreFactory> { AddRecipeStoreFactory(get(), get(), get(), get()) }
    factory { (
                  componentContext: ComponentContext,
                  onBack: () -> Unit) ->
        AddRecipeComponentImpl(
            componentContext = componentContext,
            onBack = onBack,
            storeFactory = get(),
        )
    }
}

private fun Module.recipeModule(){
    factory<RecipeStoreFactory> { RecipeStoreFactory(get(), get(), get(), get(),get()) }
    factory { (
        componentContext: ComponentContext,
        onLogOut: () -> Unit,
        onClickAddRecipe: () -> Unit) ->
        RecipeComponentImpl(
            componentContext = componentContext,
            onLogOut = onLogOut,
            onClickAddRecipe = onClickAddRecipe,
            storeFactory = get()
        )
    }
}


private fun Module.unitsModule(){
    factory<UnitsStoreFactory> { UnitsStoreFactory(get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onLogOut: () -> Unit,
        onClickBack: () -> Unit) ->
        UnitsComponentImpl(
            componentContext = componentContext,
            onLogOut = onLogOut,
            onClickBack = onClickBack,
            storeFactory = get()
        )
    }
}

private fun Module.goalModule(){
    factory<GoalStoreFactory> { GoalStoreFactory(get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onLogOut: () -> Unit,
        onClickBack: () -> Unit) ->
        GoalComponentImpl(
            componentContext = componentContext,
            onLogOut = onLogOut,
            onClickBack = onClickBack,
            storeFactory = get()
        )
    }
}

private fun Module.profileModule(){
    factory<ProfileStoreFactory> { ProfileStoreFactory(get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onLogOut: () -> Unit,
        onClickBack: () -> Unit) ->
        ProfileComponentImpl(
            componentContext = componentContext,
            onLogOut = onLogOut,
            onClickBack = onClickBack,
            storeFactory = get()
        )
    }
}

private fun Module.accountModule(){
    factory<AccountStoreFactory> { AccountStoreFactory(get(), get(), get()) }
    factory {
            (
                componentContext: ComponentContext,
                onLogOut: () -> Unit,
                onClickBack: () -> Unit
            ) ->
        AccountComponentImpl(
            componentContext = componentContext,
            onLogOut = onLogOut,
            onClickBack = onClickBack,
            storeFactory = get()
        )
    }
}

private fun Module.settingsModule(){
    factory<SettingsStoreFactory> { SettingsStoreFactory(get()) }
    factory {
            (
                componentContext: ComponentContext,
                onClickAccount: () -> Unit,
                onClickProfile: () -> Unit,
                onClickGoals: () -> Unit,
                onClickUnits: () -> Unit,
            ) ->
        SettingsComponentImpl(
            componentContext = componentContext,
            onClickAccount = onClickAccount,
            onClickProfile = onClickProfile,
            onClickGoals = onClickGoals,
            onClickUnits = onClickUnits,
            storeFactory = get()
        )
    }
}

private fun Module.challengeDetailModule(){
    factory<ChallengeDetailStoreFactory> { ChallengeDetailStoreFactory(get(), get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onClickBack: () -> Unit,
        onOpenAuth: () -> Unit,
        id: Int) ->
        ChallengeDetailComponentImpl(
            componentContext = componentContext,
            onClickBack = onClickBack,
            onOpenAuth = onOpenAuth,
            id = id,
            storeFactory = get()
        )
    }
}

private fun Module.challengeModule(){
    factory<ChallengeStoreFactory> { ChallengeStoreFactory(get(),get()) }
    factory { (
        componentContext: ComponentContext,
        onClickBack: () -> Unit,
        onOpenChallengeDetail: (Int) -> Unit,
        onOpenAuth: () -> Unit
    ) ->
        ChallengeComponentImpl(
            componentContext = componentContext,
            onClickBack = onClickBack,
            onOpenChallengeDetail = onOpenChallengeDetail,
            onOpenAuth = onOpenAuth,
            storeFactory = get()
        )
    }
}

private fun Module.mainModule(){
    factory { (
        componentContext: ComponentContext,
        onOpenAuth: () -> Unit) ->
        MainComponentImpl(
            componentContext = componentContext,
            onOpenAuth = onOpenAuth
        )
    }
}


private fun Module.tabsModule(){
    factory { params ->
        val componentContext = params.get<ComponentContext>()
        val onOpenChallenge = params.get<() -> Unit>()
        val onOpenChallengeDetail = params.get<(Int) -> Unit>()
        val onOpenAuth = params.get<() -> Unit>()
        val onClickAccount = params.get<() -> Unit>()
        val onClickProfile = params.get<() -> Unit>()
        val onClickGoals = params.get<() -> Unit>()
        val onClickUnits = params.get<() -> Unit>()
        val onClickAddRecipe = params.get<() -> Unit>()
        val onClickAppendMeal = params.get<(LocalDate, CalendarType) -> Unit>()
        val onClickDetailIngest = params.get<(LocalDate, CalendarType) -> Unit>()

        TabsComponentImpl(
            componentContext = componentContext,
            onOpenChallenge = onOpenChallenge,
            onOpenChallengeDetail = onOpenChallengeDetail,
            onOpenAuth = onOpenAuth,
            onClickAccount = onClickAccount,
            onClickProfile = onClickProfile,
            onClickGoals = onClickGoals,
            onClickUnits = onClickUnits,
            onClickAddRecipe = onClickAddRecipe,
            onClickAppendMeal = onClickAppendMeal,
            onClickDetailIngest = onClickDetailIngest,
        )
    }
}

private fun Module.homeModule(){
    factory<HomeStoreFactory> { HomeStoreFactory(get(), get(), get(), get()) }
    factory { (
        componentContext: ComponentContext,
        onOpenChallenge: () -> Unit,
        openChallengeDetail: (Int) -> Unit,
        onOpenAuth: () -> Unit) ->
        HomeComponentImpl(
            componentContext = componentContext,
            onOpenChallenge = onOpenChallenge,
            onOpenChallengeDetail = openChallengeDetail,
            onOpenAuth = onOpenAuth,
            storeFactory = get()
        )
    }
}


private fun Module.reminderModule(){
    factory<ReminderStoreFactory> { ReminderStoreFactory(get()) }
    factory { (componentContext: ComponentContext, onFinishedAuth: () -> Unit) ->
        ReminderComponentImpl(componentContext = componentContext, storeFactory = get(), onFinishedAuth = onFinishedAuth)
    }
}

private fun Module.splashModule(){
    factory<SplashStoreFactory> { SplashStoreFactory(get(), get(), get()) }
    factory {
            (
                componentContext: ComponentContext,
                onAuthorization: () -> Unit,
                onClickSignUpEmail: () -> Unit,
                onClickLogin: () -> Unit,
                accountInfoNotComplete: () -> Unit,
            ) -> SplashComponentImpl(
        componentContext = componentContext,
        storeFactory = get(),
        onAuthorization = onAuthorization,
        onClickSignUpEmail = onClickSignUpEmail,
        onClickLogin = onClickLogin,
        accountInfoNotComplete = accountInfoNotComplete
    )
    }
}

private fun Module.authModule(){
    factory { (componentContext: ComponentContext, onAuthFinished: () -> Unit) ->
        AuthComponentImpl(componentContext = componentContext, onAuthFinished)
    }
}

private fun Module.surveyModule(){
    factory<SurveyStoreFactory> { SurveyStoreFactory(get(), get(), get(),get()) }
    factory { (componentContext: ComponentContext, onSendSurvey: () -> Unit) ->
        SurveyComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onSendSurvey = onSendSurvey
        )
    }
}

private fun Module.loginModule(){
    factory<LoginStoreFactory> { LoginStoreFactory(get(), get(), get() ) }
    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onClickSignUp: () -> Unit,
                  onLogin: () -> Unit, accountInfoNotComplete: () -> Unit) ->
        LoginComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onClickSignUp = onClickSignUp,
            onLoginFinished = onLogin,
            accountInfoNotComplete = accountInfoNotComplete
        )
    }
}

private fun Module.signUpModule(){
    factory<SignUpStoreFactory> { SignUpStoreFactory(get(), get()) }
    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onLogin: () -> Unit, onSignUp: () -> Unit) ->
        SignUpComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onClickLogin = onLogin,
            signUp = onSignUp
        )
    }
}
