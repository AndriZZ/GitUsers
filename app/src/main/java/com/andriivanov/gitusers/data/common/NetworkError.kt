package com.andriivanov.gitusers.data.common

import okhttp3.Request

data class NetworkError(
    val request: Request,
    val response: retrofit2.Response<*>?,
    val errorMessage: String?
) {

    /**
     * Provides error type based on the operation that was queued
     */

    val errorType: ErrorType
        get() {
            response?.code()
            val isRetrieveOperation = request.method.equals("GET", ignoreCase = true)
            return if (isRetrieveOperation) ErrorType.Retrieve else ErrorType.Save
        }

}

enum class ErrorType {

    /**
     * Indicates an save operation failed (POST, PUT, PATCH)
     */
    Save,

    /**
     * Indicates retrieve operation failed (GET)
     */
    Retrieve

}