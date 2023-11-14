package domain.repository

import data.network.util.NetworkResult
import domain.entity.Product

interface ProductRepository {
    suspend fun getProducts(): NetworkResult<List<Product>>
    suspend fun getProduct(): NetworkResult<Product>
}