package com.ashiq.elitestore.domain.repository

import com.ashiq.elitestore.data.network.util.NetworkResult
import com.ashiq.elitestore.domain.entity.Product

interface ProductRepository {
    suspend fun getProducts(): NetworkResult<List<Product>>
}