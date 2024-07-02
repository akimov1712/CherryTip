package ru.topbun.cherry_tip.presentation.screens.auth.childs.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.utills.componentScope

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: LoginStoreFactory,
    private val onClickBack: () -> Unit,
    private val onSignUp: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore { storeFactory.store }
    override val state: StateFlow<LoginStore.State>
        get() = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    LoginStore.Label.ClickBack -> onClickBack()
                    LoginStore.Label.ClickSignUp -> onSignUp()
                }
            }
        }
    }

    override fun changeValidPassword() = store.accept(LoginStore.Intent.ChangeValidPassword)
    override fun changeVisiblePassword(value: Boolean) = store.accept(LoginStore.Intent.ChangeVisiblePassword(value))
    override fun clickBack() = store.accept(LoginStore.Intent.ClickBack)
    override fun clickSignUp() = store.accept(LoginStore.Intent.ClickSignUp)
    override fun onLogin(login: LoginEntity) = store.accept(LoginStore.Intent.OnLogin(login))
    override fun changeEmail(email: String) = store.accept(LoginStore.Intent.ChangeEmail(email))
    override fun changePassword(password: String) = store.accept(LoginStore.Intent.ChangePassword(password))

}