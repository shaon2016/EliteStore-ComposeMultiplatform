package com.ashiq.elitestore.util

suspend fun <T : Any?> handleSafely(apiCall: suspend () -> T): Result<T> {
    return try {
        val result = apiCall.invoke()
        Result.Success(result)
    } catch (e: Exception) {
        Result.Failure(e)
    }
}

