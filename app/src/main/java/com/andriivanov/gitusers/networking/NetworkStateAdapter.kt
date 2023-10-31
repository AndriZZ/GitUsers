package com.andriivanov.gitusers.networking

import com.andriivanov.gitusers.data.common.DataState
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkStateAdapter<T>(
    private val responseType: Type
) : CallAdapter<T, Call<DataState<T>>> {

    override fun responseType(): Type = this.responseType

    override fun adapt(call: Call<T>): Call<DataState<T>> = NetworkStateResponseCall(call)

}