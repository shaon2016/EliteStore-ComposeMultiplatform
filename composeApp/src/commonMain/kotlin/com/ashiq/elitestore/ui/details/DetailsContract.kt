package com.ashiq.elitestore.ui.details

import com.ashiq.elitestore.domain.entity.Product
import com.ashiq.elitestore.ui.component.ViewEvent
import com.ashiq.elitestore.ui.component.ViewSideEffect

class DetailsContract {
    sealed class Event : ViewEvent {
        data class AddToCart(val product: Product) : Event()
    }
    sealed class Effect : ViewSideEffect
}