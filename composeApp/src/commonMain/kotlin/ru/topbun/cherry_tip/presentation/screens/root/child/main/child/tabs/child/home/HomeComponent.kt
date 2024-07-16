package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {

    val state: StateFlow<HomeStore.State>

    fun addDrinkGlass()

}