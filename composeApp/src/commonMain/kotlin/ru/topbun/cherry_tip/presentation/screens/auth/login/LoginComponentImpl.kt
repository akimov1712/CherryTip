package ru.topbun.cherry_tip.presentation.screens.auth.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.LoginEntity
import ru.topbun.cherry_tip.utills.componentScope

class LoginComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onClickSignUp: () -> Unit,
) : LoginComponent, ComponentContext by componentContext {

    private lateinit var store: LoginStore
    private val scope = componentScope

    override val state: StateFlow<LoginStore.State>
        get() = store.stateFlow

    init {
        scope.launch {
            store.labels.collect{
                when(it){
                    LoginStore.Label.ClickBack -> onClickBack()
                    LoginStore.Label.ClickSignUp -> onClickSignUp()
                }
            }
        }
    }

    override fun onClickBack() = store.accept(LoginStore.Intent.ClickBack)
    override fun onClickApple() = store.accept(LoginStore.Intent.ClickApple)
    override fun onClickGoogle() = store.accept(LoginStore.Intent.ClickGoogle)
    override fun onClickFacebook() = store.accept(LoginStore.Intent.ClickFacebook)
    override fun onClickSignUp() = store.accept(LoginStore.Intent.ClickSignUp)
    override fun onClickLogin(login: LoginEntity) = store.accept(LoginStore.Intent.ClickLogin)
    override fun onChangeEmail(email: String) = store.accept(LoginStore.Intent.ChangeEmail(email))
    override fun onChangePassword(password: String) = store.accept(LoginStore.Intent.ChangePassword(password))

}