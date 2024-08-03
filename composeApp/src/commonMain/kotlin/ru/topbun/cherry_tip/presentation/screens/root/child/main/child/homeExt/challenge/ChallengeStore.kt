package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.useCases.challenge.GetChallengeUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.homeExt.challenge.ChallengeStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface ChallengeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnClick : Intent
        data class OpenChallengeDetail(val id: Int) : Intent
        data class LoadChallenge(val status: ChallengeStatus) : Intent
        data class ChangeStatusChallenge(val index: Int) : Intent
    }

    data class State(
        val items: List<ChallengeStatus>,
        val selectedIndex: Int,
        val challengeStateStatus: ChallengeState
    ) {

        sealed interface ChallengeState {
            data object Initial : ChallengeState
            data object Loading : ChallengeState
            data class Error(val text: String) : ChallengeState
            data class Result(val challenges: List<ChallengeEntity>) : ChallengeState
        }
    }

    sealed interface Label {
        data object OnClick : Label
        data class OpenChallengeDetail(val id: Int) : Label
        data object OpenAuthScreen : Label
    }
}

class ChallengeStoreFactory(
    private val storeFactory: StoreFactory,
    private val getChallengeUseCase: GetChallengeUseCase,
) {

    fun create(): ChallengeStore =
        object : ChallengeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ChallengeStore",
            initialState = State(
                items = listOf(ChallengeStatus.All, ChallengeStatus.Active, ChallengeStatus.Finished),
                selectedIndex = 0,
                challengeStateStatus = State.ChallengeState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object ChallengeLoadingStatus : Action
        data class ChallengeErrorStatus(val text: String) : Action
        data class ChallengeResultStatus(val challenges: List<ChallengeEntity>) : Action

        data object OpenAuthScreen : Action
    }

    private sealed interface Msg {
        data object ChallengeLoadingStatus : Msg
        data class ChallengeErrorStatus(val text: String) : Msg
        data class ChallengeResultStatus(val challenges: List<ChallengeEntity>) : Msg
        data class ChangeStatusChallenge(val index: Int) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.OpenAuthScreen) }) { sendChallenge() }
        }

        private suspend fun sendChallenge() {
            wrapperStoreException(
                tryBlock = {
                    dispatch(Action.ChallengeLoadingStatus)
                    val result = getChallengeUseCase(ChallengeStatus.All)
                    dispatch(Action.ChallengeResultStatus(result))
                },
                onError = {
                    dispatch(Action.ChallengeErrorStatus(it))
                }
            )
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {


        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when (intent) {
                Intent.OnClick -> publish(Label.OnClick)
                is Intent.OpenChallengeDetail -> publish(Label.OpenChallengeDetail(intent.id))
                is Intent.LoadChallenge -> {
                    sendChallenge(intent.status)
                }

                is Intent.ChangeStatusChallenge -> {
                    sendChallenge(state().items[intent.index])
                    dispatch(Msg.ChangeStatusChallenge(intent.index))
                }
            }
        }

        private fun sendChallenge(status: ChallengeStatus) {
            scope.launch(handlerTokenException{ publish(Label.OpenAuthScreen) }) {
                wrapperStoreException(
                    tryBlock = {
                        dispatch(Msg.ChallengeLoadingStatus)
                        val result = getChallengeUseCase(status)
                        dispatch(Msg.ChallengeResultStatus(result))
                    },
                    onError = {
                        dispatch(Msg.ChallengeErrorStatus(it))
                    }
                )
            }
        }

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when (action) {
                is Action.ChallengeErrorStatus -> dispatch(Msg.ChallengeErrorStatus(action.text))
                Action.ChallengeLoadingStatus -> dispatch(Msg.ChallengeLoadingStatus)
                is Action.ChallengeResultStatus -> dispatch(Msg.ChallengeResultStatus(action.challenges))
                Action.OpenAuthScreen -> publish(Label.OpenAuthScreen)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            is Msg.ChallengeErrorStatus -> copy(
                challengeStateStatus = State.ChallengeState.Error(
                    message.text
                )
            )

            Msg.ChallengeLoadingStatus -> copy(challengeStateStatus = State.ChallengeState.Loading)
            is Msg.ChallengeResultStatus -> copy(
                challengeStateStatus = State.ChallengeState.Result(
                    message.challenges
                )
            )

            is Msg.ChangeStatusChallenge -> copy(selectedIndex = message.index)
        }
    }
}
