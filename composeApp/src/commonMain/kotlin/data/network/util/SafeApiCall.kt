package data.network.util

suspend fun <T : Any?> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Loading

        val result = apiCall.invoke()
        NetworkResult.Success(result)
    } catch (e: Exception) {
        NetworkResult.Failure(e)
    }
}

