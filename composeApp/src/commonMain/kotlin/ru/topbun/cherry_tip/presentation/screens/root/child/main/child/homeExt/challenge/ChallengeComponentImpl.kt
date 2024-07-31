package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.utills.componentScope

class ChallengeComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onOpenChallengeDetail: (Int) -> Unit,
    private val onOpenAuth: () -> Unit,
    private val storeFactory: ChallengeStoreFactory
) : ChallengeComponent, ComponentContext by componentContext{

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ChallengeStore.Label.OnClick -> onClickBack()
                    is ChallengeStore.Label.OpenChallengeDetail -> onOpenChallengeDetail(it.id)
                    ChallengeStore.Label.OpenAuthScreen -> onOpenAuth()
                }
            }
        }
    }

    override fun clickBack() = store.accept(ChallengeStore.Intent.OnClick)
    override fun openChallengeDetail(id: Int) = store.accept(ChallengeStore.Intent.OpenChallengeDetail(id))
    override fun loadChallenge(status: ChallengeStatus) = store.accept(ChallengeStore.Intent.LoadChallenge(status))
    override fun choiceChallengeStatus(index: Int) = store.accept(ChallengeStore.Intent.ChangeStatusChallenge(index))

}