package edu.alatoo.kyrgyzlearning.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val throwable: Throwable, var handled: Boolean = false) : ApiResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable) = Error(throwable)

    }
}



suspend fun <T> apiRequest(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(
            withContext(Dispatchers.IO) { apiCall() }
        )
    } catch (ex: Exception) {
        Timber.e("apiRequest: ${ex.message}")
        ex.printStackTrace()
        ApiResult.Error(ex)
    }
}