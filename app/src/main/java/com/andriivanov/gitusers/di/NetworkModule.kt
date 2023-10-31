package com.andriivanov.gitusers.di

import com.andriivanov.gitusers.Global.Companion.BASE_URL
import com.andriivanov.gitusers.networking.NetworkStateAdapterFactory
import com.andriivanov.gitusers.di.ApiModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {

    companion object {

        fun provideOkHttpClient(
            interceptors: List<Interceptor> = listOf(),
            authenticator: Authenticator? = null
        ): OkHttpClient = OkHttpClient.Builder().apply {
            // interceptors
            interceptors.forEach { addInterceptor(it) }
            // authenticator
            if (authenticator != null) authenticator(authenticator)
        }.build()

        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            gson: Gson = defaultGson(),
            callAdapterFactory: CallAdapter.Factory? = null,
        ): Retrofit = Retrofit.Builder().apply {
            // settings
            client(okHttpClient)
            baseUrl(BASE_URL)
            // converters
            addConverterFactory(GsonConverterFactory.create(gson))
            // call adapters
            if (callAdapterFactory != null) addCallAdapterFactory(callAdapterFactory)
        }.build()

        private fun defaultGson(): Gson {
            val result = GsonBuilder()
            return result.create()
        }

    }
}

val networkModule = module {
    /**
     * Retrofit qualifier for public API calls
     */
    val retrofitPublic = StringQualifier("retrofit-public")
    val okHttpPublic = StringQualifier("okhttp-public")

    /**
     * APIs
     */

    single { ApiModule.provideGitUsersApi(get(retrofitPublic)) }


    /**
     * Retrofit instance for public API calls
     */
    single(retrofitPublic) {
        NetworkModule.provideRetrofit(
            okHttpClient = get(okHttpPublic),
            callAdapterFactory = NetworkStateAdapterFactory()
        )
    }

    single(okHttpPublic) {
        NetworkModule.provideOkHttpClient(
            interceptors = listOf(get<HttpLoggingInterceptor>())
        )
    }

    /**
     * Http logging interceptor
     */
    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}