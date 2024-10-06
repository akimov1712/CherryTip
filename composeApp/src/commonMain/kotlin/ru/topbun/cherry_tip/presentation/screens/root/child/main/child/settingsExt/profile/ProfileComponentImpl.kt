package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender
import ru.topbun.cherry_tip.utills.componentScope

class ProfileComponentImpl(
    componentContext: ComponentContext,
    private val onClickBack: () -> Unit,
    private val onLogOut: () -> Unit,
    private val storeFactory: ProfileStoreFactory
) : ProfileComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    ProfileStore.Label.ClickBack -> onClickBack()
                    ProfileStore.Label.LogOut -> onLogOut()
                }
            }
        }
    }

    override fun saveData() = store.accept(ProfileStore.Intent.SaveData)
    override fun load() = store.accept(ProfileStore.Intent.Load)
    override fun clickBack() = store.accept(ProfileStore.Intent.ClickBack)
    override fun changeName(name: String) = store.accept(ProfileStore.Intent.ChangeName(name))
    override fun changeSurname(surname: String) = store.accept(ProfileStore.Intent.ChangeSurname(surname))
    override fun changeCity(city: String) = store.accept(ProfileStore.Intent.ChangeCity(city))
    override fun changeGender(gender: Gender) = store.accept(ProfileStore.Intent.ChangeGender(gender))
    override fun changeBirth(birth: GMTDate) = store.accept(ProfileStore.Intent.ChangeBirth(birth))

}