package ru.topbun.cherry_tip.presentation.screens.auth.childs.signUp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class SignUpComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: SignUpStoreFactory,
    private val onClickBack: () -> Unit,
    private val onClickLogin: () -> Unit,
    private val signUp: () -> Unit,
) : SignUpComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.store }
    override val state: StateFlow<SignUpStore.State>
        get() = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    SignUpStore.Label.ClickBack -> onClickBack()
                    SignUpStore.Label.ClickLogin -> onClickLogin()
                    SignUpStore.Label.OnSignUp -> signUp()
                }
            }
        }
    }

    override fun clickBack() = store.accept(SignUpStore.Intent.ClickBack)
    override fun onSignUp() = store.accept(SignUpStore.Intent.OnSignUp)
    override fun clickLogin() = store.accept(SignUpStore.Intent.ClickLogin)
    override fun changeUsername(username: String)  = store.accept(SignUpStore.Intent.ChangeUsername(username))
    override fun changeUsernameError(value: Boolean) = store.accept(SignUpStore.Intent.ChangeUsernameError(value))
    override fun changeEmail(email: String)  = store.accept(SignUpStore.Intent.ChangeEmail(email))
    override fun changeEmailError(value: Boolean) = store.accept(SignUpStore.Intent.ChangeEmailError(value))
    override fun changePassword(password: String)  = store.accept(SignUpStore.Intent.ChangePassword(password))
    override fun changePasswordError(value: Boolean) = store.accept(SignUpStore.Intent.ChangePasswordError(value))
    override fun changeConfirmPassword(confirmPassword: String)  = store.accept(SignUpStore.Intent.ChangeConfirmPassword(confirmPassword))
    override fun changeConfirmPasswordError(value: Boolean) = store.accept(SignUpStore.Intent.ChangeConfirmPasswordError(value))
    override fun changeVisiblePassword(value: Boolean)  = store.accept(SignUpStore.Intent.ChangeVisiblePassword(value))

}