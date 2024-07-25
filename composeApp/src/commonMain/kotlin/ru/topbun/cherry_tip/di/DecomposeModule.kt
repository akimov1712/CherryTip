package ru.topbun.cherry_tip.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login.LoginStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder.ReminderStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.signUp.SignUpStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.MainComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.TabsComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home.HomeStoreFactory
import ru.topbun.cherry_tip.presentation.screens.root.child.splash.SplashComponentImpl
import ru.topbun.cherry_tip.presentation.screens.root.child.splash.SplashStoreFactory

val decomposeModule = module {
    single<StoreFactory>{ DefaultStoreFactory() }
    splashModule()
    authModule()
    loginModule()
    signUpModule()
    reminderModule()
    surveyModule()
    homeModule()
    tabsModule()
    mainModule()
    challengeModule()
}

private fun Module.challengeModule(){
    factory<ChallengeStoreFactory> { ChallengeStoreFactory(get()) }
    factory { (componentContext: ComponentContext, onClickBack: () -> Unit,) ->
        ChallengeComponentImpl(componentContext, onClickBack, get())
    }
}

private fun Module.mainModule(){
    factory { (componentContext: ComponentContext) ->
        MainComponentImpl(componentContext)
    }
}


private fun Module.tabsModule(){
    factory { (componentContext: ComponentContext, onOpenChallenge: () -> Unit) ->
        TabsComponentImpl(componentContext, onOpenChallenge)
    }
}

private fun Module.homeModule(){
    factory<HomeStoreFactory> { HomeStoreFactory(get(), get(), get()) }
    factory { (componentContext: ComponentContext, onOpenChallenge: () -> Unit) ->
        HomeComponentImpl(componentContext = componentContext, onOpenChallenge, storeFactory = get())
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
