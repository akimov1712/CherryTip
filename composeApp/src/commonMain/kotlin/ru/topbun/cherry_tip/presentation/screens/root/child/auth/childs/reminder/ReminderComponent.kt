package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.reminder

import kotlinx.coroutines.flow.StateFlow

interface ReminderComponent {

    val state: StateFlow<ReminderStore.State>

    fun setIndexSelected(index: Int)
    fun finishedAuth()


}