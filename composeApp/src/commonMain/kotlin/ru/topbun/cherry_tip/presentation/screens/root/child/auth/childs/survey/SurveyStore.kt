package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.ktor.util.date.GMTDate
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.domain.entity.user.GoalEntity
import ru.topbun.cherry_tip.domain.entity.user.ProfileEntity
import ru.topbun.cherry_tip.domain.entity.user.UnitsEntity
import ru.topbun.cherry_tip.domain.useCases.user.CreateGoalUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateProfileUseCase
import ru.topbun.cherry_tip.domain.useCases.user.CreateUnitsUseCase
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStore.Intent
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStore.Label
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.SurveyStore.State
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.active.ActiveType
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.goal.GoalType
import ru.topbun.cherry_tip.utills.ClientException
import ru.topbun.cherry_tip.utills.ConnectException
import ru.topbun.cherry_tip.utills.FailedExtractTokenException
import ru.topbun.cherry_tip.utills.ParseBackendResponseException
import ru.topbun.cherry_tip.utills.RequestTimeoutException
import ru.topbun.cherry_tip.utills.ServerException

interface SurveyStore: Store<Intent, State, Label> {

    sealed interface Intent{
        data class ChangeName(val name: String): Intent
        data class ChangeGoal(val goalType: GoalType): Intent
        data class ChangeGender(val gender: Gender): Intent
        data class ChangeAge(val age: GMTDate): Intent
        data class ChangeHeight(val height: Int): Intent
        data class ChangeWeight(val weight: Int): Intent
        data class ChangeTargetWeight(val targetWeight: Int): Intent
        data class ChangeActive(val active: ActiveType): Intent
        data object SendSurvey: Intent
        data object NextFragment: Intent
        data object PreviousFragment: Intent
    }

    data class State(
        val name: String,
        val goalType: GoalType,
        val gender: Gender,
        val age: GMTDate,
        val height: Int,
        val weight: Int,
        val targetWeight: Int,
        val active: ActiveType,
        val fragments: List<SurveyFragments>,
        val selectedFragment: SurveyFragments,
        val selectedIndex: Int,
        val progress: Float,
        val surveyState: SurveyState
    ){
        sealed interface SurveyState{
            data object Initial: SurveyState
            data object Loading: SurveyState
            data object Success: SurveyState
            data class Error(val error: String): SurveyState
        }

    }

    sealed interface Label{
        data object SendSurvey: Label
    }
}

class SurveyStoreFactory(
    private val storeFactory: StoreFactory,
    private val createProfileUseCase: CreateProfileUseCase,
    private val createGoalUseCase: CreateGoalUseCase,
    private val createUnitsUseCase: CreateUnitsUseCase,
){

    val store: Store<Intent, State, Label> = storeFactory.create(
        name = "SurveyStore",
        initialState = State(
            name = "",
            goalType = GoalType.Lose,
            gender = Gender.Female,
            age = GMTDate(getTimeMillis()),
            height = 100,
            weight = 20,
            targetWeight = 20,
            active = ActiveType.Low,
            fragments = SurveyFragments.entries,
            selectedFragment = SurveyFragments.NAME,
            selectedIndex = 0,
            progress = 0f,
            surveyState = State.SurveyState.Initial,
        ),
        bootstrapper = null,
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    )

    sealed interface Action

    sealed interface Msg{
        data class ChangeName(val name: String): Msg
        data class ChangeGoal(val goalType: GoalType): Msg
        data class ChangeGender(val gender: Gender): Msg
        data class ChangeAge(val age: GMTDate): Msg
        data class ChangeHeight(val height: Int): Msg
        data class ChangeWeight(val weight: Int): Msg
        data class ChangeTargetWeight(val targetWeight: Int): Msg
        data class ChangeActive(val active: ActiveType): Msg
        data object SendSurvey: Msg
        data object SurveyLoading: Msg
        data class SurveyError(val error: String): Msg
        data object NextFragment: Msg
        data object PreviousFragment: Msg
    }

    private inner class ExecutorImpl: CoroutineExecutor<Intent, Action, State, Msg, Label>(){
        override fun executeIntent(intent: Intent) {
            val state = state()
            when(intent){
                is Intent.ChangeActive -> dispatch(Msg.ChangeActive(intent.active))
                is Intent.ChangeAge -> dispatch(Msg.ChangeAge(intent.age))
                is Intent.ChangeGender -> dispatch(Msg.ChangeGender(intent.gender))
                is Intent.ChangeGoal -> dispatch(Msg.ChangeGoal(intent.goalType))
                is Intent.ChangeHeight -> dispatch(Msg.ChangeHeight(intent.height))
                is Intent.ChangeName -> dispatch(Msg.ChangeName(intent.name))
                is Intent.ChangeTargetWeight -> dispatch(Msg.ChangeTargetWeight(intent.targetWeight))
                is Intent.ChangeWeight -> dispatch(Msg.ChangeWeight(intent.weight))
                Intent.SendSurvey -> {
                    scope.launch {
                        dispatch(Msg.SurveyLoading)
                        try {
                            sendProfile(state)
                            sendUnits(state)
                            sendGoal(state)
                            publish(Label.SendSurvey)
                        } catch (e: ParseBackendResponseException) {
                            dispatch(Msg.SurveyError("Error while receiving data from the server"))
                        } catch (e: RequestTimeoutException) {
                            dispatch(Msg.SurveyError("Timed out"))
                        } catch (e: ClientException) {
                            dispatch(Msg.SurveyError(e.errorText))
                        } catch (e: ServerException) {
                            dispatch(Msg.SurveyError(e.errorText))
                        } catch (e: ConnectException) {
                            dispatch(Msg.SurveyError("A Failed to connect to the server, check your internet connection"))
                        } catch (e: FailedExtractTokenException) {
                            dispatch(Msg.SurveyError("Access token not available"))
                        }
                    }
                }
                Intent.NextFragment -> dispatch(Msg.NextFragment)
                Intent.PreviousFragment -> dispatch(Msg.PreviousFragment)
            }
        }

        private suspend fun sendProfile(state: State){
            val profile = ProfileEntity(
                firstName = state.name,
                lastName = null,
                birth = state.age,
                city = null,
                gender = state.gender
            )
            createProfileUseCase(profile)
        }


        private suspend fun sendGoal(state: State){
            val goal = GoalEntity(active = state.active, goalType = state.goalType)
            createGoalUseCase(goal)
        }


        private suspend fun sendUnits(state: State){
            val units = UnitsEntity(
                weight = state.weight,
                height = state.height,
                targetWeight = state.targetWeight,
                bloodGlucose = null
            )
            createUnitsUseCase(units)
        }

    }

    private object ReducerImpl: Reducer<State, Msg>{
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.ChangeActive -> copy(active = msg.active)
            is Msg.ChangeAge -> copy(age = msg.age)
            is Msg.ChangeGender -> copy(gender = msg.gender)
            is Msg.ChangeGoal -> copy(goalType = msg.goalType)
            is Msg.ChangeHeight -> copy(height = msg.height)
            is Msg.ChangeName -> copy(name = msg.name)
            is Msg.ChangeTargetWeight -> copy(targetWeight = msg.targetWeight)
            is Msg.ChangeWeight -> copy(weight = msg.weight)
            is Msg.SurveyError -> copy(surveyState = State.SurveyState.Error(msg.error))
            Msg.SurveyLoading -> copy(surveyState = State.SurveyState.Loading)
            Msg.NextFragment -> {
                val nextFragment = fragments[selectedIndex + 1]
                val progress = (selectedIndex + 2f) / fragments.size
                copy(selectedFragment = nextFragment, progress = progress, selectedIndex = selectedIndex + 1)
            }
            Msg.PreviousFragment -> {
                val previousFragment = fragments[selectedIndex - 1]
                val progress = (selectedIndex.toFloat()) / fragments.size
                copy(selectedFragment = previousFragment, progress = progress, selectedIndex = selectedIndex-1)
            }
            Msg.SendSurvey -> copy(surveyState = State.SurveyState.Success)
        }

    }

}