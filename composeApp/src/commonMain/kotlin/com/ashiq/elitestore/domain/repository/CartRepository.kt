package com.ashiq.elitestore.domain.repository

import com.ashiq.elitestore.domain.entity.CartItem
import com.ashiq.elitestore.util.Result

interface CartRepository {
    suspend fun insert(cartItem: CartItem): Result<Unit>
    suspend fun getAll(): Result<List<CartItem>>
    suspend fun updateQuantity(productId: Int, quantity: Int): Result<Unit>
    suspend fun deleteCartItem(productId: Int): Result<Unit>
    suspend fun clearCart(): Result<Unit>
}