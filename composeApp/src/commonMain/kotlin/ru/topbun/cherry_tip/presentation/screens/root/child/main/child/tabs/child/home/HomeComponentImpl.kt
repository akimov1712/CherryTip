package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.tabs.child.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import ru.topbun.cherry_tip.utills.componentScope


class HomeComponentImpl(
    componentContext: ComponentContext,
    private val onOpenChallenge: () -> Unit,
    private val onOpenChallengeDetail: () -> Unit,
    private val onOpenAuth: () -> Unit,
    private val storeFactory: HomeStoreFactory
): HomeComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect{
                when(it){
                    HomeStore.Label.OpenChallengeScreen -> onOpenChallenge()
                    HomeStore.Label.OpenChallengeDetailScreen -> onOpenChallengeDetail()
                    HomeStore.Label.OpenAuthScreen -> onOpenAuth()
                }
            }
        }
    }
    override fun addDrinkGlass() = store.accept(HomeStore.Intent.AddDrinkGlass)
    override fun openChallenge() = store.accept(HomeStore.Intent.OpenChallengeScreen)
    override fun openChallengeDetail() = store.accept(HomeStore.Intent.OpenChallengeDetailScreen)
}