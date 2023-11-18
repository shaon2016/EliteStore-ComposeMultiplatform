package com.ashiq.elitestore.ui.home

import com.ashiq.elitestore.domain.entity.Product
import com.ashiq.elitestore.ui.component.ViewEvent
import com.ashiq.elitestore.ui.component.ViewSideEffect
import com.ashiq.elitestore.util.UIState

class HomeContract {
    sealed class Event : ViewEvent {
        data object Initialize : Event()
        data object ToCartScreen : Event()
        data class ToDetails(val product: Product) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object ToCartScreen : Navigation()
            data class ToDetails(val product: Product) : Navigation()
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val products: List<Product> = listOf(),
        val UIState: UIState = com.ashiq.elitestore.util.UIState.INITIAL
    )
}