package ru.topbun.cherry_tip.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStoreFactory
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp.SignUpStoreFactory
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.survey.SurveyStoreFactory

val componentModule = module {
    single<StoreFactory>{ DefaultStoreFactory() }

    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onClickSignUp: () -> Unit, onLogin: () -> Unit) ->
        LoginComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onClickSignUp = onClickSignUp,
            onLogin = onLogin
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
}

val storeModule = module {
    single<LoginStoreFactory> { LoginStoreFactory(get(), get()) }
    single<SignUpStoreFactory> { SignUpStoreFactory(get(), get(), get()) }
    factory<SurveyStoreFactory> { SurveyStoreFactory(get(), get(), get(),get()) }
}
