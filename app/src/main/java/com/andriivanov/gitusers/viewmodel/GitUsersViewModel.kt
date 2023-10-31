package com.andriivanov.gitusers.viewmodel

import androidx.lifecycle.MutableLiveData
import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.common.loading
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.repository.GitUsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


class GitUsersViewModel(
    private val gitUsersRepository: GitUsersRepository
) : BaseViewModel() {

    companion object {
        const val DELAY_GET_USER = 1000L
    }

    private val _usersFromSearch = MutableLiveData<DataState<List<UserItem>>>()
    val usersFromSearch = _usersFromSearch

    private val _userDetails = MutableLiveData<DataState<UserDetails>>()
    val userDetails = _userDetails

    private var getUsersJob: Job? = null

    fun getUsersByQuery(query: String) {
        _usersFromSearch.loading()
        getUsersJob?.cancel()
        getUsersJob = launchIO {
            // Added delay before starting the request
            delay(DELAY_GET_USER)
            gitUsersRepository.getUsersByQuery(query).collect { result ->
                _usersFromSearch.postValue(result)
            }
        }
    }

    fun getUserDetails(username: String) {
        _userDetails.loading()
        getUsersJob?.cancel()
        getUsersJob = launchIO {
            // Added delay before starting the request
            delay(DELAY_GET_USER)
            val result = gitUsersRepository.getUser(username)
            _userDetails.postValue(result)
        }
    }
}


