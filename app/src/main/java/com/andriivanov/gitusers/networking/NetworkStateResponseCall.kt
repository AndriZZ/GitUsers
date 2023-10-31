package com.andriivanov.gitusers.networking

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.common.NetworkError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkStateResponseCall<T>(private val delegate: Call<T>) : Call<DataState<T>> {

    override fun enqueue(callback: Callback<DataState<T>>) = delegate.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkStateResponseCall,
                            Response.success(DataState.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkStateResponseCall,
                            Response.success(DataState.Success(Unit as T))
                        )
                    }
                } else {
                    val errorMessage = if (response.message().isNullOrBlank()) {
                        "Error code: ${response.code()}"
                    } else {
                        response.message()
                    }
                    val networkError = NetworkError(
                        request = call.request(),
                        response = response,
                        errorMessage = errorMessage
                    )
                    callback.onResponse(
                        this@NetworkStateResponseCall,
                        Response.success(DataState.Failure(networkError))
                    )
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                throwable.printStackTrace()

                val networkError = NetworkError(
                    request = call.request(),
                    response = null,
                    errorMessage = null
                )
                callback.onResponse(
                    this@NetworkStateResponseCall,
                    Response.success(
                        DataState.Failure(networkError)
                    )
                )
            }
        }
    )

    override fun execute(): Response<DataState<T>> {
        throw UnsupportedOperationException("NetworkStateResponseCall doesn't support execute()")
    }

    override fun clone(): Call<DataState<T>> = NetworkStateResponseCall(delegate.clone())

    override fun request(): Request = delegate.request()

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun timeout(): Timeout = delegate.timeout()

    private fun Response<T>.toErrorResponse(): Response<Any>? {
        val errorBody = this.errorBody()?.string()

        val typeOfResponse =
            TypeToken.getParameterized(
                Response::class.java,
                Any::class.java
            ).type

        return Gson().fromJson<Response<Any>>(errorBody, typeOfResponse)
    }

}