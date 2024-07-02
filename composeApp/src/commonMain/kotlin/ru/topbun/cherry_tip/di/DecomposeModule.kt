package ru.topbun.cherry_tip.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginComponentImpl
import ru.topbun.cherry_tip.presentation.screens.auth.childs.login.LoginStoreFactory

val componentModule = module {
    single<StoreFactory>{ DefaultStoreFactory() }

    factory { (componentContext: ComponentContext, onClickBack: () -> Unit, onSignUp: () -> Unit) ->
        LoginComponentImpl(
            componentContext = componentContext,
            storeFactory = get(),
            onClickBack = onClickBack,
            onSignUp = onSignUp
        )
    }
}

val storeModule = module {
    single<LoginStoreFactory> { LoginStoreFactory(get(), get()) }
}
