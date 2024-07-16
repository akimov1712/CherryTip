package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow


class HomeComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: HomeStoreFactory
): HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    override fun addDrinkGlass() = store.accept(HomeStore.Intent.AddDrinkGlass)
}