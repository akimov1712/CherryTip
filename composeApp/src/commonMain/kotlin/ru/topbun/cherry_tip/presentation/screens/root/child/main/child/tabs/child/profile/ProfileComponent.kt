package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.profile

import kotlinx.coroutines.flow.StateFlow

interface ProfileComponent {

    val state: StateFlow<ProfileStore.State>

    fun clickAccount()
    fun clickProfile()
    fun clickGoals()
    fun clickUnits()

}