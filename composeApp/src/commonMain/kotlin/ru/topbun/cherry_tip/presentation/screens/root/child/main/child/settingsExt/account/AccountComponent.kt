package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account

import kotlinx.coroutines.flow.StateFlow

interface AccountComponent {

    val state: StateFlow<AccountStore.State>

    fun logOut()
    fun clickBack()

}