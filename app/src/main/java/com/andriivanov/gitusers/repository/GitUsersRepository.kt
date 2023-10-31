package com.andriivanov.gitusers.repository

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.mapper.UserDetailsMapper
import com.andriivanov.gitusers.data.mapper.UserItemMapper
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.networking.GitUsersApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GitUsersRepository(
    private val gitUsersApi: GitUsersApi,
    private val userItemMapper: UserItemMapper,
    private val userDetailsMapper: UserDetailsMapper
) {

    suspend fun getUsersByQuery(query: String): Flow<DataState<List<UserItem>>> {
        return flow {
            val response = gitUsersApi.getUsersByQuery(query)
            val result = userItemMapper.mapData(response)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUser(username: String): DataState<UserDetails> {
        val result = gitUsersApi.getUserDetails(username)
        return userDetailsMapper.mapData(result)
    }

}