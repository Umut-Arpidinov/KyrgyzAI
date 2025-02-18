package edu.alatoo.kyrgyzlearning.common.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

sealed class LocalDbResult<out R> {
    data class Success<out T>(val data: T) : LocalDbResult<T>()
    data class Error(val throwable: Throwable, var handled: Boolean = false) :
        LocalDbResult<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable) = Error(throwable)
    }
}


suspend fun <T> dbRequest(apiCall: suspend () -> T): LocalDbResult<T> {
    return try {
        LocalDbResult.Success(
            withContext(Dispatchers.IO) { apiCall() }
        )
    } catch (ex: Exception) {
        Timber.e("Db request: ${ex.message}")
        ex.printStackTrace()
        LocalDbResult.Error(ex)
    }
}
