package com.ashiq.elitestore.domain.entity
data class CartItem(
    val productId: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val quantity: Int
)
