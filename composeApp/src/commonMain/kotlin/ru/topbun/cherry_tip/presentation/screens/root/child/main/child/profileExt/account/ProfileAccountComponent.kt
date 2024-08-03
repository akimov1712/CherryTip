package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.profileExt.account

import kotlinx.coroutines.flow.StateFlow

interface ProfileAccountComponent {

    val state: StateFlow<ProfileAccountStore.State>

    fun logOut()
    fun clickBack()

}