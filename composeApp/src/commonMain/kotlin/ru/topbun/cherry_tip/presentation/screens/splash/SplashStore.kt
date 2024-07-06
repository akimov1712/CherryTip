package ru.topbun.cherry_tip.presentation.screens.splash

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.useCases.user.CheckAccountInfoCompleteUseCase
import ru.topbun.cherry_tip.domain.useCases.user.TokenIsValidUseCase
import ru.topbun.cherry_tip.presentation.screens.splash.SplashStore.Intent
import ru.topbun.cherry_tip.presentation.screens.splash.SplashStore.Label
import ru.topbun.cherry_tip.presentation.screens.splash.SplashStore.State
import ru.topbun.cherry_tip.utills.AccountInfoNotComplete
import ru.topbun.cherry_tip.utills.FailedExtractToken

interface SplashStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnSignUpEmail : Intent
        data object OnLogin : Intent
    }

    data class State(
        val splashState: SplashState,
    ){
        sealed interface SplashState{
            data object Initial: SplashState
            data object NotAuth: SplashState
        }
    }

    sealed interface Label {
        data object AccountInfoNotComplete : Label
        data object OnAuth : Label
        data object OnSignUpEmail : Label
        data object OnLogin : Label
    }
}

class SplashStoreFactory(
    private val storeFactory: StoreFactory,
    private val tokenIsValidUseCase: TokenIsValidUseCase,
    private val checkAccountInfoCompleteUseCase: CheckAccountInfoCompleteUseCase
) {

    fun create(): SplashStore =
        object : SplashStore, Store<Intent, State, Label> by storeFactory.create(
            name = "SplashStore",
            initialState = State(
                splashState = State.SplashState.Initial
            ),
            bootstrapper = Bootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    sealed interface Action{
        data object OnAuth: Action
        data object NotAuth: Action
        data object NotAccountInfoComplete: Action
    }

    private sealed interface Msg {
        data object NotAuth : Msg
    }

    inner class Bootstrapper: CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                try {
                    tokenIsValidUseCase()
                    checkAccountInfoCompleteUseCase()
                    dispatch(Action.OnAuth)
                } catch (e: FailedExtractToken){
                    dispatch(Action.NotAuth)
                } catch (e: AccountInfoNotComplete){
                    dispatch(Action.NotAccountInfoComplete)
                }
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                Action.NotAccountInfoComplete -> publish(Label.AccountInfoNotComplete)
                Action.NotAuth -> dispatch(Msg.NotAuth)
                Action.OnAuth -> publish(Label.OnAuth)
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.OnLogin -> publish(Label.OnLogin)
                Intent.OnSignUpEmail -> publish(Label.OnSignUpEmail)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            Msg.NotAuth -> copy(splashState = State.SplashState.NotAuth)
        }
    }
}
