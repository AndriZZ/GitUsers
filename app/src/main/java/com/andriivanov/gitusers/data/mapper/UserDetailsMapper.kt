package com.andriivanov.gitusers.data.mapper

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.data.serv.UserDetailsServ

class UserDetailsMapper : IMapper<UserDetailsServ, UserDetails> {

    override fun map(serv: UserDetailsServ?): UserDetails {
        return UserDetails(
            id = serv?.id ?: -1,
            avatarUrl = serv?.avatarUrl ?: "",
            name = serv?.name ?: "",
            bio = serv?.bio ?: "",
            email = serv?.email ?: "",
            blog = serv?.blog ?: "",
            company = serv?.company ?: "",
            location = serv?.location ?: "",
            hireable = serv?.hireable ?: false,
            following = serv?.following ?: 0,
            followers = serv?.followers ?: 0,
            repos = serv?.repos ?: 0,
            privateRepos = serv?.privateRepos ?: 0
        )
    }

    fun mapData(serv: DataState<UserDetailsServ>): DataState<UserDetails> {
        return when (serv) {
            is DataState.Loading -> DataState.Loading()
            is DataState.Failure -> DataState.Failure(serv.error)
            is DataState.Success -> DataState.Success(map(serv.result))
        }
    }
}