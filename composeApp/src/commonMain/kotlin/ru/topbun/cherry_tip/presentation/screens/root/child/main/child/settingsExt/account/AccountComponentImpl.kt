package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class AccountComponentImpl(
    componentContext: ComponentContext,
    private val onLogOut: () -> Unit,
    private val onClickBack: () -> Unit,
    private val storeFactory: AccountStoreFactory,
) : AccountComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    AccountStore.Label.LogOut -> onLogOut()
                    AccountStore.Label.ClickBack -> onClickBack()
                }
            }
        }
    }

    override fun logOut() = store.accept(AccountStore.Intent.LogOut)
    override fun clickBack() = store.accept(AccountStore.Intent.ClickBack)
}