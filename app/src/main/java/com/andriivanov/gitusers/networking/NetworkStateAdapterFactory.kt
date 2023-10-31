package com.andriivanov.gitusers.networking

import com.andriivanov.gitusers.data.common.DataState
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkStateAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {

        val observableType = (returnType as ParameterizedType?)?.let { getParameterUpperBound(0, it) }
        val rawObservableType = observableType?.let { getRawType(it) }
        require(rawObservableType == DataState::class.java) { "type must be a resource" }
        require(observableType is ParameterizedType) { "resource must be parameterized" }

        val bodyType = getParameterUpperBound(0, observableType)
        return NetworkStateAdapter<Any>(bodyType)
    }

}