package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings

import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {

    val state: StateFlow<ProfileStore.State>

    fun clickAccount()
    fun clickProfile()
    fun clickGoals()
    fun clickUnits()

}