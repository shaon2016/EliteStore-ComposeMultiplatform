package data.repository

import data.mapper.toDomain
import data.network.productlist.ProductResponseItem
import data.network.util.NetworkResult
import data.network.util.safeApiCall
import domain.entity.Product
import domain.repository.ProductRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ProductRepositoryImpl(private val client: HttpClient) : ProductRepository {
    override suspend fun getProducts(): NetworkResult<List<Product>> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val json = client.get(urlString = "products").bodyAsText()
                val response = Json.decodeFromString<List<ProductResponseItem>>(json)

                response.map { it.toDomain() }
            }
        }
    }

    override suspend fun getProduct(): NetworkResult<Product> {
        TODO("Not yet implemented")
    }
}