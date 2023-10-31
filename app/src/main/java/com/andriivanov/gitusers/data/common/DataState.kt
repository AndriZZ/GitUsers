package com.andriivanov.gitusers.data.common

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Wrapper class for network state
 */
sealed class DataState<out T> {

    /**
     * Indicates that the resource is loading
     */
    class Loading<out T> : DataState<T>()

    /**
     * Indicates that the resource is successfully loaded,
     * [result] will be automatically cast to non null
     */
    class Success<out T>(val result: T) : DataState<T>()

    /**
     * Indicates that the resource failed to load,
     * [error] contains info about the error
     */
    class Failure<out T>(val error: NetworkError) : DataState<T>()

    override fun toString(): String {
        return "loading: ${isLoading()} success: ${isSuccess()} failure: ${isFailure()}"
    }

}

fun <T> DataState<T>.getDataState(): DataState<Any> = when (this) {
    is DataState.Loading -> DataState.Loading()
    is DataState.Success -> DataState.Success(this)
    is DataState.Failure -> DataState.Failure(this.error)
}

/**
 * Checks if [DataState] is loading
 */
fun <T> DataState<T>.isLoading(): Boolean = this is DataState.Loading

/**
 * Checks if [DataState] is success
 */
fun <T> DataState<T>.isSuccess(): Boolean = this is DataState.Success

/**
 * Checks if [DataState] is failure
 */
fun <T> DataState<T>.isFailure(): Boolean = this is DataState.Failure

/**
 * Invokes [action] when [DataState] is [DataState.Loading]
 */
inline fun <T> DataState<T>.onLoading(action: () -> Unit): DataState<T> {
    if (this is DataState.Loading) action.invoke()
    return this
}

/**
 * Invokes [action] when [DataState] is [DataState.Loading]
 * [action] will be invoked on the UI thread
 */
suspend inline fun <T> DataState<T>.onLoadingMain(crossinline action: () -> Unit): DataState<T> {
    if (this is DataState.Loading) withContext(Dispatchers.Main) { action.invoke() }
    return this
}

/**
 * Invokes [action] when [DataState] is [DataState.Success]
 */
inline fun <T> DataState<T>.onSuccess(action: (data: T) -> Unit): DataState<T> {
    if (this is DataState.Success) action.invoke(this.result)
    return this
}

/**
 * Invokes [action] when [DataState] is [DataState.Success]
 * [action] will be invoked on the UI thread
 */
suspend inline fun <T> DataState<T>.onSuccessMain(crossinline action: (data: T) -> Unit): DataState<T> {
    if (this is DataState.Success) withContext(Dispatchers.Main) { action.invoke(this@onSuccessMain.result) }
    return this
}

/**
 * Invokes [action] when [DataState] is [DataState.Failure]
 */
inline fun <T> DataState<T>.onFailure(action: (error: NetworkError) -> Unit): DataState<T> {
    if (this is DataState.Failure) action.invoke(error)
    return this
}

/**
 * Gets successful response or @null
 */
fun <T> DataState<T>.getOrNull(): T? {
    return if (this is DataState.Success) this.result else null
}

/**
 * Sets live data value to loading
 */
fun <T> MutableLiveData<DataState<T>>.loading() {
    this.value = DataState.Loading()
}

fun <T> MutableLiveData<DataState<T>>.postLoading() {
    this.postValue(DataState.Loading())
}

/**
 * Sets live data value to success
 */
fun <T> MutableLiveData<DataState<T>>.success(data: T) {
    this.value = DataState.Success(data)
}

fun <T> MutableLiveData<DataState<T>>.postSuccess(data: T) {
    this.postValue(DataState.Success(data))
}

/**
 * Sets live data value to failure
 */
fun <T> MutableLiveData<DataState<T>>.failure(error: NetworkError) {
    this.value = DataState.Failure(error)
}

fun <T> MutableLiveData<DataState<T>>.postFailure(error: NetworkError) {
    this.postValue(DataState.Failure(error))
}

/**
 * Converts Any object to [DataState.Success]
 */
inline fun <reified T> Any.asNetworkSuccess(): DataState.Success<T> {
    return DataState.Success(this as T)
}