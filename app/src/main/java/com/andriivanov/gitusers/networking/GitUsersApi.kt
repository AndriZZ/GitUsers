package com.andriivanov.gitusers.networking

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.serv.UserDetailsServ
import com.andriivanov.gitusers.data.serv.UserItemsServ
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitUsersApi {
    @GET("search/users?")
    suspend fun getUsersByQuery(
        @Query("q") q: String
    ): DataState<UserItemsServ>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): DataState<UserDetailsServ>
}