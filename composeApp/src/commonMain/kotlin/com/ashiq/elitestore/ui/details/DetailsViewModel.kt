package com.ashiq.elitestore.ui.details

import com.ashiq.elitestore.domain.entity.CartItem
import com.ashiq.elitestore.domain.entity.Product
import com.ashiq.elitestore.domain.repository.CartRepository
import com.ashiq.elitestore.ui.component.BaseViewModel
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class DetailsViewModel(private val cartRepository: CartRepository) :
    BaseViewModel<DetailsContract.Event, DetailsContract.Effect>() {

    override fun handleEvents(event: DetailsContract.Event) {
        when (event) {
            is DetailsContract.Event.AddToCart -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.insert(
                CartItem(
                    productId = product.id,
                    title = product.title,
                    imageUrl = product.image,
                    price = product.price,
                    quantity = 1
                )
            )
        }
    }

}