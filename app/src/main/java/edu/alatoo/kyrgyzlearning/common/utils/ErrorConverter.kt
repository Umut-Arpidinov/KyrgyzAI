package edu.alatoo.kyrgyzlearning.common.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import edu.alatoo.kyrgyzlearning.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

data class MessageException(@StringRes val stringResId: Int) : Exception()

data class StringMessageException(val msg: String) : Exception()


class ErrorConverter(
    private val resources: Resources,
) {
    fun convert(error: Throwable?): String =
        when (error) {
            is HttpException -> getMessageFrom(error)
            is MessageException -> resources.getString(error.stringResId)
            is StringMessageException -> error.msg
            is SocketTimeoutException -> resources.getString(R.string.error_timeout)
            is IOException -> resources.getString(R.string.error_network_error)
            else -> resources.getString(R.string.error_unknown_error)
        }

    private fun getMessageFrom(exception: HttpException): String {
        if (exception.code() == 401) {
            Timber.d(exception.message)
        }
        return try {
            if (exception.code() == 504) resources.getString(R.string.error_gateway_timeout)
            else {
                val str = exception.response()?.errorBody()?.string()!!
                val jsonObject = JSONObject(str)
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val errorMessage = jsonObject.optString(key)
                    if (errorMessage.isNotEmpty()) {
                        return getDisplayMessage(errorMessage)
                    }
                }
                resources.getString(R.string.error_unknown_error)
            }

        } catch (e: Exception) {
            resources.getString(R.string.error_unknown_error)
        }
    }

    fun isAuthenticationError(error: Throwable?): Boolean {
        return error != null && error is HttpException && error.code() == 401
    }


    private fun getDisplayMessage(message: String): String {
        return try {
            val jsonArray = JSONArray(message)
            val messages = mutableListOf<String>()
            for (i in 0 until jsonArray.length()) {
                messages.add(jsonArray.getString(i))
            }
            messages.joinToString(separator = "\n")
        } catch (e: JSONException) {
            message
        }
    }
}