package data.network.util

sealed class NetworkResult<out T> {
    data class Success<out T : Any?>(val data: T) : NetworkResult<T>()
    data class Failure(val exception: Exception) : NetworkResult<Nothing>()
}
