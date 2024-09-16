package ru.topbun.cherry_tip.utills

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

val ComponentContext.componentScope
    get() = CoroutineScope(Dispatchers.Main + SupervisorJob()).apply {
        doOnDestroy { cancel() }
    }
