package com.andriivanov.gitusers.di

import com.andriivanov.gitusers.networking.GitUsersApi
import retrofit2.Retrofit

class ApiModule {

    companion object {

        fun provideGitUsersApi(retrofit: Retrofit): GitUsersApi {
            return retrofit.create(GitUsersApi::class.java)
        }
    }
}