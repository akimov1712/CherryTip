package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.LogOutUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.account.AccountStore.State
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException

interface AccountStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object LogOut: Intent
        data object ClickBack: Intent
    }

    data class State(
        val profileState: AccountState
    ){

        sealed interface AccountState{
            data object Initial: AccountState
            data object Loading: AccountState
            data class Error(val text: String): AccountState
            data class Result(val userId: String): AccountState
        }

    }

    sealed interface Label {
        data object LogOut: Label
        data object ClickBack: Label
    }
}

class AccountStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val logOutUseCase: LogOutUseCase
) {

    fun create(): AccountStore =
        object : AccountStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AccountAccountStore",
            initialState = State(State.AccountState.Initial),
            executorFactory = ::ExecutorImpl,
            bootstrapper = Bootstrapper(),
            reducer = ReducerImpl
        ) {}

    private inner class Bootstrapper: CoroutineBootstrapper<Action>(){
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.AccountStateLoading)
                    val userId = getAccountInfoUseCase().id
                    dispatch(Action.AccountStateResult(userId))
                }){
                    dispatch(Action.AccountStateError(it))
                }
            }
        }
    }

    private sealed interface Action {
        data class AccountStateError(val text: String): Action
        data object AccountStateLoading: Action
        data class AccountStateResult(val userId: String): Action

        data object LogOut: Action
    }

    private sealed interface Msg {
        data class AccountStateError(val text: String): Msg
        data object AccountStateLoading: Msg
        data class AccountStateResult(val userId: String): Msg
    }


    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.LogOut -> {
                    scope.launch(handlerTokenException { publish(Label.LogOut) } ) {
                        wrapperStoreException({
                            logOutUseCase()
                            publish(Label.LogOut)
                        }){}
                    }
                }

                Intent.ClickBack -> publish(Label.ClickBack)
            }
        }

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.AccountStateError -> dispatch(Msg.AccountStateError(action.text))
                Action.AccountStateLoading -> dispatch(Msg.AccountStateLoading)
                is Action.AccountStateResult -> dispatch(Msg.AccountStateResult(action.userId))
                Action.LogOut -> publish(Label.LogOut)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State = when (message) {
            is Msg.AccountStateError -> copy(profileState = State.AccountState.Error(message.text))
            Msg.AccountStateLoading -> copy(profileState = State.AccountState.Loading)
            is Msg.AccountStateResult -> copy(profileState = State.AccountState.Result(message.userId))
        }
    }
}
