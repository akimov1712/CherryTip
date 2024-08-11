package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.units

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class UnitsComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onLogOut: () -> Unit,
    private val storeFactory: UnitsStoreFactory
) : UnitsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    UnitsStore.Label.ClickBack -> onClickBack()
                    UnitsStore.Label.LogOut -> onLogOut()
                }
            }
        }
    }

    override fun saveData() = store.accept(UnitsStore.Intent.SaveData)
    override fun clickBack() = store.accept(UnitsStore.Intent.ClickBack)
    override fun changeWeight(weight: Int) = store.accept(UnitsStore.Intent.ChangeWeight(weight))
    override fun changeTargetWeight(targetWeight: Int) = store.accept(UnitsStore.Intent.ChangeTargetWeight(targetWeight))
    override fun changeHeight(height: Int) = store.accept(UnitsStore.Intent.ChangeHeight(height))
    override fun changeBloodGlucose(bloodGlucose: Int) = store.accept(UnitsStore.Intent.ChangeBloodGlucose(bloodGlucose))
}