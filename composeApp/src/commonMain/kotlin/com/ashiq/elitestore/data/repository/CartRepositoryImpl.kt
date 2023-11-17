package com.ashiq.elitestore.data.repository

import com.ashiq.elitestore.data.local.Database
import com.ashiq.elitestore.data.local.DatabaseDriverFactory
import com.ashiq.elitestore.domain.entity.CartItem
import com.ashiq.elitestore.domain.repository.CartRepository
import com.ashiq.elitestore.util.Result
import com.ashiq.elitestore.util.handleSafely
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CartRepositoryImpl(databaseDriverFactory: DatabaseDriverFactory) : CartRepository {
    private var db: Database

    init {
        db = Database(databaseDriverFactory)
    }

    override suspend fun insert(cartItem: CartItem): Result<Unit> {
        return withContext(Dispatchers.IO) {
            handleSafely {
                val oldCartItem =
                    db.getAll().filter { item -> item.productId == cartItem.productId }

                if (oldCartItem.isNotEmpty()) {
                    //  if item exists then we increment it
                    val item = oldCartItem.first()
                    val qty = item.quantity + 1

                    db.updateQuantity(cartItem.productId, qty)
                } else {
                    // new item so inserting
                    db.insert(cartItem)
                }
            }
        }
    }

    override suspend fun getAll(): Result<List<CartItem>> {
        return withContext(Dispatchers.IO) {
            handleSafely { db.getAll() }
        }
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            handleSafely { db.updateQuantity(productId, quantity) }
        }
    }

    override suspend fun deleteCartItem(productId: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            handleSafely { db.deleteCartItem(productId) }
        }
    }

    override suspend fun clearCart(): Result<Unit> {
        return withContext(Dispatchers.IO) {
            handleSafely { db.clearCart() }
        }
    }
}