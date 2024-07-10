package ru.topbun.cherry_tip.presentation.screens.auth.childs.reminder

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class ReminderComponentImpl(
    componentContext: ComponentContext,
    private val storeFactory: ReminderStoreFactory,
    private val onFinishedAuth: () -> Unit
) : ReminderComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ReminderStore.Label.FinishedStarted -> onFinishedAuth
                }
            }
        }
    }

    override fun setIndexSelected(index: Int) = store.accept(ReminderStore.Intent.SetIndexSelected(index))
    override fun finishedAuth() = store.accept(ReminderStore.Intent.FinishedStarted)
}