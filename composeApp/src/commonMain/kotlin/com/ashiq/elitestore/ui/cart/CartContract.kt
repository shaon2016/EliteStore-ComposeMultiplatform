package com.ashiq.elitestore.ui.cart

import com.ashiq.elitestore.domain.entity.CartItem
import com.ashiq.elitestore.ui.component.ViewEvent
import com.ashiq.elitestore.ui.component.ViewSideEffect

class CartContract {
    sealed class Event : ViewEvent {
        data object NavigateBack : Event()
        data object Initialize : Event()
        data object PlaceOrder : Event()
        data object HideOrderDialog : Event()
        data class Increment(val productId: Int, val quantity: Int) : Event()
        data class Decrement(val productId: Int, val quantity: Int) : Event()
        data class Remove(val productId: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data object NavigateBack : Navigation()
            data object NavigateBackToHome : Navigation()
        }
    }

    data class State(
        val cartItems: List<CartItem> = listOf(),
        val total: Double = 0.0,
        val showOrderDialog: Boolean = false
    )
}