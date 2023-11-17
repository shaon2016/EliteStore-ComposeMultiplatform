package com.ashiq.elitestore.data.local

import com.ashiq.elitestore.domain.entity.CartItem

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    fun insert(cartItem: CartItem) {
        dbQuery.insertCartItem(
            productId = cartItem.productId.toLong(),
            title = cartItem.title,
            price = cartItem.price,
            quantity = 1,
            imageUrl = cartItem.imageUrl
        )
    }

    fun getAll(): List<CartItem> {
        return dbQuery.getAll(
            mapper = { _, productId, title, price, quantity, imageUrl ->
                CartItem(
                    productId = productId.toInt(),
                    title = title,
                    price = price,
                    quantity = quantity.toInt(),
                    imageUrl = imageUrl
                )
            }
        ).executeAsList()
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        dbQuery.updateQuantity(quantity.toLong(), productId.toLong())
    }

    fun deleteCartItem(productId: Int) {
        dbQuery.deleteCartItem(productId.toLong())
    }

    fun clearCart() {
        dbQuery.transaction {
            dbQuery.deleteAll()
        }
    }
}