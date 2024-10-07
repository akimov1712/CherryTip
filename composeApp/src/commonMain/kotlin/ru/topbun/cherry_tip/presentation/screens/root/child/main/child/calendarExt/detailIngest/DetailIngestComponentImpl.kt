package ru.topbun.cherry_tip.presentation.screens.root.child.main.child.calendarExt.detailIngest

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ru.topbun.cherry_tip.domain.entity.calendar.CalendarType
import ru.topbun.cherry_tip.utills.componentScope

class DetailIngestComponentImpl(
    componentContext: ComponentContext,
    private val date: LocalDate,
    private val calendarType: CalendarType,
    private val onClickBack: () -> Unit,
    private val onClickAddMeal: () -> Unit,
    private val onOpenAuth: () -> Unit,
    private val storeFactory: DetailIngestStoreFactory
) : DetailIngestComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(date, calendarType) }
    override val state = store.stateFlow

    init {
        componentScope.launch {
            store.labels.collect {
                when (it) {
                    DetailIngestStore.Label.ClickBack -> onClickBack()
                    DetailIngestStore.Label.OpenAuth -> onOpenAuth()
                    DetailIngestStore.Label.ClickAddMeal -> onClickAddMeal()
                }
            }
        }
    }

    override fun load() = store.accept(DetailIngestStore.Intent.Load)
    override fun clickBack() = store.accept(DetailIngestStore.Intent.ClickBack)
    override fun clickAddMeal() = store.accept(DetailIngestStore.Intent.ClickAddMeal)
    override fun cancelRecipe(id: Int) = store.accept(DetailIngestStore.Intent.CancelRecipe(id))
}