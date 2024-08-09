package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStore
import ru.topbun.cherry_tip.utills.componentScope

class SettingsComponentImpl (
    componentContext: ComponentContext,
    private val onClickAccount: () -> Unit,
    private val onClickProfile: () -> Unit,
    private val onClickGoals: () -> Unit,
    private val onClickUnits: () -> Unit,
    private val storeFactory: SettingsStoreFactory
): SettingsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                   SettingsStore.Label.ClickAccount -> onClickAccount()
                   SettingsStore.Label.ClickProfile -> onClickProfile()
                   SettingsStore.Label.ClickGoals -> onClickGoals()
                   SettingsStore.Label.ClickUnits -> onClickUnits()
                }
            }
        }
    }

    override fun clickAccount() = store.accept(SettingsStore.Intent.ClickAccount)
    override fun clickProfile() = store.accept(SettingsStore.Intent.ClickProfile)
    override fun clickGoals() = store.accept(SettingsStore.Intent.ClickGoals)
    override fun clickUnits() = store.accept(SettingsStore.Intent.ClickUnits)

}