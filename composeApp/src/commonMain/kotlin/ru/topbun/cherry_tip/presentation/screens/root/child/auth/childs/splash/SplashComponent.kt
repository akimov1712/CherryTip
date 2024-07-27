package ru.topbun.cherry_tip.presentation.screens.root.child.auth.childs.splash

import kotlinx.coroutines.flow.StateFlow

interface SplashComponent {

    val state: StateFlow<SplashStore.State>

    fun onSignUpEmail()
    fun onLogin()
    fun runChecks()

}