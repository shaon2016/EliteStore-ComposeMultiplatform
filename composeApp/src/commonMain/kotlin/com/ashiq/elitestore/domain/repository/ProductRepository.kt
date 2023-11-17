package com.ashiq.elitestore.domain.repository

import com.ashiq.elitestore.util.Result
import com.ashiq.elitestore.domain.entity.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}