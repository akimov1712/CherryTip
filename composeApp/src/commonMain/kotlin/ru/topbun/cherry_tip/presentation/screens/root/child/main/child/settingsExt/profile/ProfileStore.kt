package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.useCases.user.GetAccountInfoUseCase
import ru.topbun.cherry_tip.domain.useCases.user.UpdateProfileUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStore.State
import ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile.ProfileStoreFactory.Action
import ru.topbun.cherry_tip.utills.formatToString
import ru.topbun.cherry_tip.utills.handlerTokenException
import ru.topbun.cherry_tip.utills.wrapperStoreException



interface ProfileStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object SaveData: Intent
        data object ClickBack: Intent

        data class ChangeName(val name: String): Intent
        data class ChangeSurname(val surname: String): Intent
        data class ChangeCity(val city: String): Intent
        data class ChangeGender(val gender: Gender): Intent
        data class ChangeBirth(val birth: GMTDate): Intent
    }

    data class State(
        val name: String,
        val surname: String,
        val city: String,
        val gender: Gender,
        val birth: GMTDate,
        val profileState: ProfileState
    ){

        sealed interface ProfileState{
            data object Initial: ProfileState
            data object Loading: ProfileState
            data class Error(val text: String): ProfileState
            data object Result: ProfileState
        }

    }

    sealed interface Label {
        data object ClickBack: Label
        data object LogOut: Label
    }
}

class ProfileStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) {

    fun create(): ProfileStore =
        object : ProfileStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = State(
                name = "",
                surname = "",
                city = "",
                gender = Gender.Male,
                birth = GMTDate.START,
                profileState = State.ProfileState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class AccountStateError(val text: String): Action
        data object AccountStateLoading: Action
        data class AccountStateResult(
            val name: String,
            val surname: String,
            val city: String,
            val gender: Gender,
            val birth: GMTDate
        ): Action

        data object LogOut: Action
    }

    private sealed interface Msg {
        data class ChangeName(val name: String): Msg
        data class ChangeSurname(val surname: String): Msg
        data class ChangeCity(val city: String): Msg
        data class ChangeGender(val gender: Gender): Msg
        data class ChangeBirth(val birth: GMTDate): Msg

        data class AccountStateError(val text: String): Msg
        data object AccountStateLoading: Msg
        data class AccountStateResult(
            val name: String,
            val surname: String,
            val city: String,
            val gender: Gender,
            val birth: GMTDate
        ): Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch(handlerTokenException { dispatch(Action.LogOut) }) {
                wrapperStoreException({
                    dispatch(Action.AccountStateLoading)
                    val accountInfo = getAccountInfoUseCase()
                    dispatch(Action.AccountStateResult(
                        name = accountInfo.profile?.firstName ?: "",
                        surname = accountInfo.profile?.lastName ?: "",
                        city = accountInfo.profile?.city ?: "",
                        gender = accountInfo.profile?.gender ?: Gender.Male,
                        birth = accountInfo.profile?.birth ?: GMTDate.START
                    ))
                }){
                    dispatch(Action.AccountStateError(it))
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeAction(action: Action) {
            super.executeAction(action)
            when(action){
                is Action.AccountStateError -> dispatch(Msg.AccountStateError(action.text))
                Action.AccountStateLoading -> dispatch(Msg.AccountStateLoading)
                is Action.AccountStateResult -> dispatch(Msg.AccountStateResult(
                    name = action.name,
                    surname = action.surname,
                    city = action.city,
                    gender = action.gender,
                    birth = action.birth
                ))
                Action.LogOut -> publish(Label.ClickBack)
            }
        }

        override fun executeIntent(intent: Intent) {
            super.executeIntent(intent)
            when(intent){
                is Intent.ChangeBirth -> dispatch(Msg.ChangeBirth(intent.birth))
                is Intent.ChangeCity -> dispatch(Msg.ChangeCity(intent.city))
                is Intent.ChangeGender -> dispatch(Msg.ChangeGender(intent.gender))
                is Intent.ChangeName -> dispatch(Msg.ChangeName(intent.name))
                is Intent.ChangeSurname -> dispatch(Msg.ChangeSurname(intent.surname))
                Intent.ClickBack -> publish(Label.ClickBack)
                Intent.SaveData -> {
                    scope.launch(handlerTokenException { publish(Label.LogOut) }) {
                        wrapperStoreException({
                            val state = state()
                            updateProfileUseCase(
                                ProfileEntity(
                                    firstName = state.name,
                                    lastName = state.surname,
                                    birth = state.birth,
                                    city = state.city,
                                    gender = state.gender
                                )
                            )
                            publish(Label.ClickBack)}
                        ){
                            dispatch(Msg.AccountStateError(it))
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.AccountStateError -> copy(profileState = State.ProfileState.Error(message.text))
                Msg.AccountStateLoading -> copy(profileState = State.ProfileState.Loading)
                is Msg.AccountStateResult -> copy(
                    name = message.name,
                    surname = message.surname,
                    city = message.city,
                    gender = message.gender,
                    birth = message.birth,
                    profileState = State.ProfileState.Result
                )
                is Msg.ChangeBirth -> copy(birth = message.birth)
                is Msg.ChangeCity -> copy(city = message.city)
                is Msg.ChangeGender -> copy(gender = message.gender)
                is Msg.ChangeName -> copy(name = message.name)
                is Msg.ChangeSurname -> copy(surname = message.surname)
            }
    }
}
