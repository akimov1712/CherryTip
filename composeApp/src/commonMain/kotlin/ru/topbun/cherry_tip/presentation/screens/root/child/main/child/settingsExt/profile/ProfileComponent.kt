package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.settingsExt.profile

import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.StateFlow
import ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.survey.fragments.gender.Gender

interface ProfileComponent {

    val state: StateFlow<ProfileStore.State>

    fun saveData()
    fun clickBack()

    fun changeName(name: String)
    fun changeSurname(surname: String)
    fun changeCity(city: String)
    fun changeGender(gender: Gender)
    fun changeBirth(birth: GMTDate)

}