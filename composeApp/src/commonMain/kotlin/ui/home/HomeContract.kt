package ui.home

import domain.entity.Product
import ui.DataState
import ui.ViewEvent
import ui.ViewSideEffect

class HomeContract {
    sealed class Event : ViewEvent {
        data object Initialize : Event()
    }

    sealed class Effect : ViewSideEffect {}

    data class State(
        val isLoading: Boolean = false,
        val products: List<Product> = listOf(),
        val dataState: DataState = DataState.INITIAL
    )
}