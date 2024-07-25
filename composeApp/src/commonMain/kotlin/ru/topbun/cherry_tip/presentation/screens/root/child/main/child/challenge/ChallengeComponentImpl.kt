package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class ChallengeComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val storeFactory: ChallengeStoreFactory
) : ChallengeComponent, ComponentContext by componentContext{

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ChallengeStore.Label.OnClick -> onClickBack()
                }
            }
        }
    }

    override fun clickBack() = store.accept(ChallengeStore.Intent.OnClick)

}