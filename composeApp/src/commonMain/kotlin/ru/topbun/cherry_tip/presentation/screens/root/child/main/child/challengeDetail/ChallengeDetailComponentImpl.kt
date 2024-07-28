package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope

class ChallengeDetailComponentImpl(
    componentContext: ComponentContext,
    private val id: Int,
    private val onClickBack: () -> Unit,
    private val onOpenAuth: () -> Unit,
    private val storeFactory: ChallengeDetailStoreFactory
) : ChallengeDetailComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(id) }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ChallengeDetailStore.Label.ClickBack -> onClickBack()
                    ChallengeDetailStore.Label.OpenAuthScreen -> onOpenAuth()
                }
            }
        }
    }

    override fun clickBack() = store.accept(ChallengeDetailStore.Intent.ClickBack)

}