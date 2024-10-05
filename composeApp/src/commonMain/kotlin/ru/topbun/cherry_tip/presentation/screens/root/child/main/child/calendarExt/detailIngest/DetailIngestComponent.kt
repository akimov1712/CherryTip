package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest

import kotlinx.coroutines.flow.StateFlow

interface DetailIngestComponent {

    val state: StateFlow<DetailIngestStore.State>

    fun clickBack()
    fun clickAddMeal()
    fun cancelRecipe(id: Int)

}