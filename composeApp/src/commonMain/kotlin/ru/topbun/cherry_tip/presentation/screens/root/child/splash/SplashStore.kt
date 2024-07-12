package ru.topbun.cherry_tip.presentation.screens.root.child.splash

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.client.network.sockets.ConnectTimeoutException
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.useCases.user.CheckAccountInfoCompleteUseCase
import ru.topbun.cherry_tip.domain.useCases.user.TokenIsValidUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.splash.SplashStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.splash.SplashStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.splash.SplashStore.State
import ru.topbun.cherry_tip.utills.AccountInfoNotCompleteException
import ru.topbun.cherry_tip.utills.FailedExtractTokenException

interface SplashStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object OnSignUpEmail : Intent
        data object OnLogin : Intent
        data object RunChecks : Intent
    }

    data class State(
        val splashState: SplashState,
    ){
        sealed interface SplashState{
            data object Initial: SplashState
            data class Error(val message: String): SplashState
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
            bootstrapper = null,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}


    sealed interface Action

    private sealed interface Msg {
        data object NotAuth : Msg
        data class SplashError(val message: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                Intent.OnLogin -> publish(Label.OnLogin)
                Intent.OnSignUpEmail -> publish(Label.OnSignUpEmail)
                Intent.RunChecks -> {
                    scope.launch {
                        try {
                            tokenIsValidUseCase()
                            checkAccountInfoCompleteUseCase()
                            publish(Label.OnAuth)
                        } catch (e: FailedExtractTokenException){
                            dispatch(Msg.NotAuth)
                        } catch (e: AccountInfoNotCompleteException){
                            publish(Label.AccountInfoNotComplete)
                        } catch (e: ConnectTimeoutException){
                            dispatch(Msg.SplashError("Check your internet connection"))
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg) = when (message) {
            Msg.NotAuth -> copy(splashState = State.SplashState.NotAuth)
            is Msg.SplashError -> copy(splashState = State.SplashState.Error(message.message))
        }
    }
}
