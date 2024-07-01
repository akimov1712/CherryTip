package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.utills.componentScope

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onClickSignUp: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {

    private lateinit var store: LoginStore

    override val state: StateFlow<LoginStore.State>
        get() = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    LoginStore.Label.ClickBack -> onClickBack()
                    LoginStore.Label.ClickSignUp -> onClickSignUp()
                }
            }
        }
    }

    override fun clickBack() = store.accept(LoginStore.Intent.ClickBack)
    override fun clickSignUp() = store.accept(LoginStore.Intent.ClickSignUp)
    override fun onLogin(login: LoginEntity) = store.accept(LoginStore.Intent.OnLogin(login))
    override fun changeEmail(email: String) = store.accept(LoginStore.Intent.ChangeEmail(email))
    override fun changePassword(password: String) = store.accept(LoginStore.Intent.ChangePassword(password))

}