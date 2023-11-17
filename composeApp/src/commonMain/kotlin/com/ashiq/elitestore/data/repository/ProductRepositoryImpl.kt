package com.ashiq.elitestore.data.repository

import com.ashiq.elitestore.data.mapper.toDomain
import com.ashiq.elitestore.data.network.productlist.ProductResponseItem
import com.ashiq.elitestore.util.Result
import com.ashiq.elitestore.util.handleSafely
import com.ashiq.elitestore.domain.entity.Product
import com.ashiq.elitestore.domain.repository.ProductRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ProductRepositoryImpl(private val client: HttpClient) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            handleSafely {
                val json = client.get(urlString = "products").bodyAsText()
                val response = Json.decodeFromString<List<ProductResponseItem>>(json)

                response.map { it.toDomain() }
            }
        }
    }
}