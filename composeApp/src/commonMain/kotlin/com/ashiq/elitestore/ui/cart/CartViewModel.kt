package com.ashiq.elitestore.ui.cart

import com.ashiq.elitestore.domain.repository.CartRepository
import com.ashiq.elitestore.ui.component.BaseViewModel
import com.ashiq.elitestore.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class CartViewModel(private val cartRepository: CartRepository) :
    BaseViewModel<CartContract.Event, CartContract.Effect>() {
    private val _state = MutableStateFlow(CartContract.State())
    val state = _state.asStateFlow()

    override fun handleEvents(event: CartContract.Event) {
        when (event) {
            is CartContract.Event.NavigateBack -> setEffect {
                CartContract.Effect.Navigation.NavigateBack
            }

            is CartContract.Event.Initialize -> {
                initialize {
                    loadCartItems()
                }
            }

            is CartContract.Event.Increment -> {
                updateQuantity(event.productId, event.quantity)
            }

            is CartContract.Event.Decrement -> {
                updateQuantity(event.productId, event.quantity)
            }

            is CartContract.Event.PlaceOrder -> {
                placeOrder()
            }

            is CartContract.Event.Remove -> {
                removeCartItem(event.productId)
            }

            is CartContract.Event.HideOrderDialog -> {
                _state.update { it.copy(showOrderDialog = false) }
                setEffect {
                    CartContract.Effect.Navigation.NavigateBackToHome
                }
            }
        }
    }

    private fun placeOrder() {
        if (_state.value.cartItems.isNotEmpty()) {
            viewModelScope.launch {
                when (val result = cartRepository.clearCart()) {
                    is Result.Success -> {
                        _state.update { it.copy(showOrderDialog = true) }
                    }

                    is Result.Failure -> {
                        Napier.i("Failed to place order")
                    }
                }
            }
        }
    }

    private fun removeCartItem(productId: Int) {
        viewModelScope.launch {
            when (val result = cartRepository.remove(productId)) {
                is Result.Success -> {
                    loadCartItems()
                }

                is Result.Failure -> {
                    Napier.i("Failed to update quantity")
                }
            }
        }
    }

    private fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            when (cartRepository.updateQuantity(productId, quantity)) {
                is Result.Success -> {
                    loadCartItems()
                }

                is Result.Failure -> {
                    Napier.i("Failed to update quantity")
                }
            }
        }
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            when (val result = cartRepository.getAll()) {
                is Result.Success -> {
                    _state.update { it.copy(cartItems = result.data) }
                    calculateTotal()
                }

                is Result.Failure -> {
                    Napier.i("Failed to load items")
                }
            }
        }
    }

    private fun calculateTotal() {
        val items = _state.value.cartItems
        var total = 0.0
        items.forEach {
            total += (it.price * it.quantity)
        }

        _state.update { it.copy(total = total) }
    }
}