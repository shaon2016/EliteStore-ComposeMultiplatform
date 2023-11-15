package ui.home

import domain.entity.Product
import ui.component.ViewEvent
import ui.component.ViewSideEffect
import util.UIState

class HomeContract {
    sealed class Event : ViewEvent {
        data object Initialize : Event()
    }

    sealed class Effect : ViewSideEffect

    data class State(
        val isLoading: Boolean = false,
        val products: List<Product> = listOf(),
        val UIState: UIState = util.UIState.INITIAL
    )
}