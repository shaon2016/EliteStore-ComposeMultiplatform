package data.network.util

sealed class NetworkResult<out T> {
    data class Success<out T : Any?>(val data: T) : NetworkResult<T>()
    data class Failure(val exception: Exception) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}

inline fun <T : Any?> NetworkResult<T>.isLoading(crossinline action: (isLoading: Boolean) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Loading) action(true) else action(false)
    return this
}

inline fun <T : Any?> NetworkResult<T>.onSuccess(crossinline action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(this.data)
    return this
}

inline fun <T : Any?> NetworkResult<T>.onFailure(crossinline action: (exception: Exception) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Failure) action(this.exception)
    return this
}
