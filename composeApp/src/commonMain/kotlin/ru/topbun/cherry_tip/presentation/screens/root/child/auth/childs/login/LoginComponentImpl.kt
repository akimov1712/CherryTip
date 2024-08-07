package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: LoginStoreFactory,
    private val onClickBack: () -> Unit,
    private val onClickSignUp: () -> Unit,
    private val onLoginFinished: () -> Unit,
    private val accountInfoNotComplete: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore { storeFactory.store }
    override val state: StateFlow<LoginStore.State>
        get() = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    LoginStore.Label.ClickBack -> onClickBack()
                    LoginStore.Label.ClickSignUp -> onClickSignUp()
                    LoginStore.Label.OnLogin -> onLoginFinished()
                    LoginStore.Label.AccountInfoNotComplete -> accountInfoNotComplete()
                }
            }
        }
    }

    override fun changeVisiblePassword(value: Boolean) = store.accept(LoginStore.Intent.ChangeVisiblePassword(value))
    override fun clickBack() = store.accept(LoginStore.Intent.ClickBack)
    override fun clickSignUp() = store.accept(LoginStore.Intent.ClickSignUp)
    override fun onLogin() = store.accept(LoginStore.Intent.OnLogin)
    override fun changeEmail(email: String) = store.accept(LoginStore.Intent.ChangeEmail(email))
    override fun changePassword(password: String) = store.accept(LoginStore.Intent.ChangePassword(password))

}