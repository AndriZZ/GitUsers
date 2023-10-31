package com.andriivanov.gitusers.data.mapper

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.data.serv.UserItemsServ
import com.andriivanov.gitusers.data.serv.UserItemServ

class UserItemMapper : IMapper<UserItemServ, UserItem> {

    override fun map(serv: UserItemServ?): UserItem {
        return UserItem(
            avatarUrl = serv?.avatarUrl ?: "",
            id = serv?.id ?: -1,
            login = serv?.login ?: ""
        )
    }

    fun mapData(serv: DataState<UserItemsServ>): DataState<List<UserItem>> {
        return when (serv) {
            is DataState.Loading -> DataState.Loading()
            is DataState.Failure -> DataState.Failure(serv.error)
            is DataState.Success -> DataState.Success(
                serv.result.userItems?.map { map(it) } ?: listOf()
            )
        }
    }
}