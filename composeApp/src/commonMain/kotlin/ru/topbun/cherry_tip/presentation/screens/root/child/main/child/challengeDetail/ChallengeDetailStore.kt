package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeEntity
import ru.topbun.cherry_tip.domain.entity.challenge.ChallengeStatus
import ru.topbun.cherry_tip.domain.entity.challenge.UserChallengeEntity
import ru.topbun.cherry_tip.domain.useCases.challenge.CancelChallengeUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.GetUserChallengeByIdUseCase
import ru.topbun.cherry_tip.domain.useCases.challenge.StartChallengeUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.challengeDetail.ChallengeDetailStore.State
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.ClientException
import ru.topbun.cherry_tip.utills.FailedExtractTokenException
import ru.topbun.cherry_tip.utills.RequestTimeoutException

interface ChallengeDetailStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack: Intent
        data object ChangeStatusChallenge: Intent
    }

    data class State(
        val challengeState: ChallengeState,
        val changeStatusState: ChangeStatusState
    ){

        sealed interface ChangeStatusState{
            data object Calmly: ChangeStatusState
            data object Loading: ChangeStatusState
        }

        sealed interface ChallengeState{
            data object Initial: ChallengeState
            data object Loading: ChallengeState
            data class Error(val text: String): ChallengeState
            data class Success(val challenge: ChallengeEntity): ChallengeState
        }
    }

    sealed interface Label {
        data object ClickBack: Label
        data object OpenAuthScreen : Label
    }
}

class ChallengeDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getUserChallengeByIdUseCase: GetUserChallengeByIdUseCase,
    private val startChallengeUseCase: StartChallengeUseCase,
    private val cancelChallengeUseCase: CancelChallengeUseCase,
) {

    fun create(id: Int): ChallengeDetailStore =
        object : ChallengeDetailStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ChallengeDetailStore",
            initialState = State(
                challengeState = State.ChallengeState.Initial,
                changeStatusState = State.ChangeStatusState.Calmly
            ),
            bootstrapper = BootstrapperImpl(id),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data object ChallengeLoading: Action
        data class ChallengeError(val text: String): Action
        data class ChallengeSuccess(val challenge: ChallengeEntity): Action
        data object OpenAuthScreen : Action
    }

    private sealed interface Msg {
        data object ChallengeLoading: Msg
        data class ChallengeError(val text: String): Msg
        data class ChallengeSuccess(val challenge: ChallengeEntity): Msg

        data class ChangeStatusChallenge(val userChallenge: UserChallengeEntity): Msg
        data object ChangeStatusLoading: Msg
        data object ChangeStatusCalmly: Msg
    }

    private inner class BootstrapperImpl(private val id: Int) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            val handlerTokenException = CoroutineExceptionHandler { _, throwable ->
                if (throwable is FailedExtractTokenException) dispatch(Action.OpenAuthScreen)
            }

            scope.launch(handlerTokenException) { sendChallenge() }
        }

        private suspend fun sendChallenge() {
            try {
                dispatch(Action.ChallengeLoading)
                val result = getUserChallengeByIdUseCase(id)
                dispatch(Action.ChallengeSuccess(result))
            } catch (e: AccountInfoNotCompleteException) {
                dispatch(Action.ChallengeError("Account info not complete"))
            } catch (e: RequestTimeoutException) {
                dispatch(Action.ChallengeError("Check internet connection"))
            } catch (e: ClientException) {
                dispatch(Action.ChallengeError(e.errorText))
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.ClickBack -> publish(Label.ClickBack)
                is Intent.ChangeStatusChallenge -> {
                    val handlerTokenException = CoroutineExceptionHandler { _, throwable ->
                        if (throwable is FailedExtractTokenException) publish(Label.OpenAuthScreen)
                    }
                    scope.launch(handlerTokenException) { sendChangeStatus() }
                }
            }
        }

        private suspend fun sendChangeStatus() {
            try {
                dispatch(Msg.ChangeStatusLoading)
                val isStarted = (state().challengeState as? State.ChallengeState.Success)
                    ?.challenge
                    ?.userChallenge
                    ?.status == ChallengeStatus.Active
                val id = (state().challengeState as? State.ChallengeState.Success)?.challenge?.id
                    ?: run { throw ClientException("Failed to start challenge") }
                val result = if (isStarted) cancelChallengeUseCase(id) else startChallengeUseCase(id)
                dispatch(Msg.ChangeStatusChallenge(result))
            } catch (e: AccountInfoNotCompleteException) {
                dispatch(Msg.ChallengeError("Account info not complete"))
            } catch (e: RequestTimeoutException) {
                dispatch(Msg.ChallengeError("Check internet connection"))
            } catch (e: ClientException) {
                dispatch(Msg.ChallengeError(e.errorText))
            } finally {
                dispatch(Msg.ChangeStatusCalmly)
            }
        }

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.ChallengeError -> dispatch(Msg.ChallengeError(action.text))
                Action.ChallengeLoading -> dispatch(Msg.ChallengeLoading)
                is Action.ChallengeSuccess -> dispatch(Msg.ChallengeSuccess(action.challenge))
                Action.OpenAuthScreen -> publish(Label.OpenAuthScreen)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.ChallengeError -> copy(
                challengeState = State.ChallengeState.Error(message.text)
            )
            Msg.ChallengeLoading -> copy(
                challengeState = State.ChallengeState.Loading
            )
            is Msg.ChallengeSuccess -> copy(
                challengeState = State.ChallengeState.Success(message.challenge)
            )
            Msg.ChangeStatusCalmly -> copy(changeStatusState = State.ChangeStatusState.Calmly)
            is Msg.ChangeStatusChallenge -> {
                when (val currentState = challengeState) {
                    is State.ChallengeState.Success -> {
                        copy(
                            challengeState = currentState.copy(
                                challenge = currentState.challenge.copy(
                                    userChallenge = message.userChallenge
                                )
                            )
                        )
                    } else -> {
                    Napier.d{"COPY: ${this}"}
                    this
                }
                }
            }
            Msg.ChangeStatusLoading -> copy(changeStatusState = State.ChangeStatusState.Loading)
        }
    }
}
