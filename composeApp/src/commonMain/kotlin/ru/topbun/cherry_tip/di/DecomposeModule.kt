package ru.topbun.cherry_tip.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.topbun.cherry_tip.presentation.screens.auth.AuthComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStoreFactory
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder.ReminderStoreFactory
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpStoreFactory
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyStoreFactory
import ru.topbun.cherry_tip.presentation.screens.splash.SplashComponentImpl
import ru.topbun.cherry_tip.presentation.screens.splash.SplashStoreFactory

val componentModule = module {
    single<StoreFactory>{ DefaultStoreFactory() }

    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onClickSignUp: () -> Unit, onLogin: () -> Unit) ->
        LoginComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onClickSignUp = onClickSignUp,
            onLoginFinished = onLogin
        )
    }
    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onLogin: () -> Unit, onSignUp: () -> Unit) ->
        SignUpComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onClickLogin = onLogin,
            signUp = onSignUp
        )
    }
    factory { (componentContext: ComponentContext, onSendSurvey: () -> Unit) ->
        SurveyComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onSendSurvey = onSendSurvey
        )
    }

    factory { (componentContext: ComponentContext) ->
        AuthComponentImpl(componentContext = componentContext)
    }

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

    factory { (componentContext: ComponentContext, onFinishedAuth: () -> Unit) ->
        ReminderComponentImpl(componentContext = componentContext, storeFactory = get(), onFinishedAuth = onFinishedAuth)
    }
}

val storeModule = module {
    factory<LoginStoreFactory> { LoginStoreFactory(get(), get()) }
    factory<SignUpStoreFactory> { SignUpStoreFactory(get(), get(), get()) }
    factory<SurveyStoreFactory> { SurveyStoreFactory(get(), get(), get(),get()) }
    factory<SplashStoreFactory> { SplashStoreFactory(get(), get(), get()) }
    factory<ReminderStoreFactory> { ReminderStoreFactory(get()) }
}
