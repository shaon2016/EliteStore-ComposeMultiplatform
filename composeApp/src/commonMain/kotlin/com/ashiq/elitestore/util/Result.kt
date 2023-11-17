package com.ashiq.elitestore.util

sealed class Result<out T> {
    data class Success<out T : Any?>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}
