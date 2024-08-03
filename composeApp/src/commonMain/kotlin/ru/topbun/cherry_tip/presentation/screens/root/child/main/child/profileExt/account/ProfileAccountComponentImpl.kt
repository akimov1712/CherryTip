package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class ProfileAccountComponentImpl(
    componentContext: ComponentContext,
    private val onLogOut: () -> Unit,
    private val onClickBack: () -> Unit,
    private val storeFactory: ProfileAccountStoreFactory,
) : ProfileAccountComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ProfileAccountStore.Label.LogOut -> onLogOut()
                    ProfileAccountStore.Label.ClickBack -> onClickBack()
                }
            }
        }
    }

    override fun logOut() = store.accept(ProfileAccountStore.Intent.LogOut)
    override fun clickBack() = store.accept(ProfileAccountStore.Intent.ClickBack)
}