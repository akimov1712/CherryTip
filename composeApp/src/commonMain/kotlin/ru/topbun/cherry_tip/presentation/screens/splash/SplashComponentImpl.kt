package ru.topbun.cherry_tip.presentation.screens.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class SplashComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SplashStoreFactory,
    private val onAuthorization: () -> Unit,
    private val onClickSignUpEmail: () -> Unit,
    private val onClickLogin: () -> Unit,
    private val accountInfoNotComplete: () -> Unit,
) : SplashComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    SplashStore.Label.OnAuth -> onAuthorization()
                    SplashStore.Label.OnSignUpEmail -> onClickSignUpEmail()
                    SplashStore.Label.OnLogin -> onClickLogin()
                    SplashStore.Label.AccountInfoNotComplete -> accountInfoNotComplete()
                }
            }
        }
    }

    override fun onSignUpEmail() = store.accept(SplashStore.Intent.OnSignUpEmail)
    override fun onLogin() = store.accept(SplashStore.Intent.OnLogin)
    override fun runChecks() = store.accept(SplashStore.Intent.RunChecks)
}