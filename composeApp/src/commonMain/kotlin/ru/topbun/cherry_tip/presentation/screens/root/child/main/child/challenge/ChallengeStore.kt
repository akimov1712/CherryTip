package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.client.utils.EmptyContent.status
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.useCases.challenge.GetChallengeUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challenge.ChallengeStore.State
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.ClientException
import ru.topbun.cherry_tip.utills.FailedExtractTokenException
import ru.topbun.cherry_tip.utills.RequestTimeoutException

interface ChallengeStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnClick : Intent
        data object OpenChallengeDetail : Intent
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
        data object OpenChallengeDetail : Label
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
                items = ChallengeStatus.entries.toList(),
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
            val handlerTokenException = CoroutineExceptionHandler { _, throwable ->
                if (throwable is FailedExtractTokenException) dispatch(Action.OpenAuthScreen)
            }

            scope.launch(handlerTokenException) { sendChallenge() }
        }

        private suspend fun sendChallenge() {
            try {
                dispatch(Action.ChallengeLoadingStatus)
                val result = getChallengeUseCase(ChallengeStatus.All)
                dispatch(Action.ChallengeResultStatus(result))
            } catch (e: AccountInfoNotCompleteException) {
                dispatch(Action.ChallengeErrorStatus(e.message ?: ""))
            } catch (e: RequestTimeoutException) {
                dispatch(Action.ChallengeErrorStatus(e.message ?: ""))
            } catch (e: ClientException) {
                dispatch(Action.ChallengeErrorStatus(e.errorText))
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {


        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when (intent) {
                Intent.OnClick -> publish(Label.OnClick)
                Intent.OpenChallengeDetail -> publish(Label.OpenChallengeDetail)
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
            val handlerTokenException = CoroutineExceptionHandler { _, throwable ->
                if (throwable is FailedExtractTokenException) publish(Label.OpenAuthScreen)
            }

            scope.launch(handlerTokenException) {
                try {
                    dispatch(Msg.ChallengeLoadingStatus)
                    val result = getChallengeUseCase(status)
                    dispatch(Msg.ChallengeResultStatus(result))
                } catch (e: AccountInfoNotCompleteException) {
                    dispatch(Msg.ChallengeErrorStatus(e.message ?: ""))
                } catch (e: RequestTimeoutException) {
                    dispatch(Msg.ChallengeErrorStatus(e.message ?: ""))
                } catch (e: ClientException) {
                    dispatch(Msg.ChallengeErrorStatus(e.errorText))
                }
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
