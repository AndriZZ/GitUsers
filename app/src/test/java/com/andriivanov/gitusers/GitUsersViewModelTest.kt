package com.andriivanov.gitusers

import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.mapper.UserDetailsMapper
import com.andriivanov.gitusers.data.mapper.UserItemMapper
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.networking.GitUsersApi
import com.andriivanov.gitusers.repository.GitUsersRepository
import com.andriivanov.gitusers.viewmodel.GitUsersViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GitUsersViewModelTest {
    @Mock
    private lateinit var gitUsersApi: GitUsersApi
    private lateinit var userItemMapper: UserItemMapper
    private lateinit var userDetailsMapper: UserDetailsMapper
    private lateinit var gitUsersRepository: GitUsersRepository
    private lateinit var viewModel: GitUsersViewModel

    companion object {
        private const val TEST_USER_NAME = "AndriZZ"
        private val testUser = UserDetails(
            id = 23562744,
            name = "Andri Ivanov",
            email = "",
            blog = "",
            company = "",
            location = "",
            hireable = false,
            bio = "",
            avatarUrl = "https://avatars.githubusercontent.com/u/23562744?v=4",
            following = 0,
            followers = 1,
            repos = 15,
            privateRepos = 2
        )
    }

    @Before
    fun setup() {
        gitUsersApi = mock(GitUsersApi::class.java)
        userDetailsMapper = mock(UserDetailsMapper::class.java)
        userItemMapper = mock(UserItemMapper::class.java)
        gitUsersRepository = GitUsersRepository(
            gitUsersApi,
            userItemMapper,
            userDetailsMapper
        )
        viewModel = GitUsersViewModel(gitUsersRepository)
    }

    @Test
    fun gitUsersViewModel_verify_gitUsersRepository_expected_user() {
        runBlocking {
            val expected = DataState.Success(testUser)
            Mockito.`when`(gitUsersRepository.getUser(TEST_USER_NAME)).thenReturn(expected)
            // Verify that the API method is called with the correct username
            Mockito.verify(gitUsersApi).getUserDetails(TEST_USER_NAME)
            // Call the method under test
            val result = gitUsersRepository.getUser(TEST_USER_NAME)
            // Verify that the result matches the expected
            assert(result == expected)
        }
    }
}