package com.andriivanov.gitusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andriivanov.gitusers.R
import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.common.ErrorType
import com.andriivanov.gitusers.data.common.isLoading
import com.andriivanov.gitusers.data.common.onFailure
import com.andriivanov.gitusers.data.common.onSuccess
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.databinding.FragmentUserListBinding
import com.andriivanov.gitusers.ui.recyclerview.adapter.GitUsersAdapter
import com.andriivanov.gitusers.utils.showDialog
import com.andriivanov.gitusers.utils.visibleOrGone
import com.andriivanov.gitusers.utils.withInternet
import com.andriivanov.gitusers.viewmodel.GitUsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class GitUserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding

    private val gitUsersViewModel: GitUsersViewModel by viewModel()
    private val gitUsersAdapter: GitUsersAdapter by lazy {
        GitUsersAdapter(
            onGitUserClick = { user ->
                gitUsersViewModel.getUserDetails(user.login)
                withInternet {
                    navigateToUserDetails(user)
                }
            }
        )
    }
    private val handleUsersFiltered = Observer<DataState<List<UserItem>>> { result ->
        binding.progressBar.visibleOrGone(result.isLoading())
        result.onSuccess { users ->
            gitUsersAdapter.submitList(users)
        }
        result.onFailure {
            showDialog(
                title = getString(R.string.error_general),
                message = it.errorMessage ?: when (it.errorType) {
                    ErrorType.Save -> getString(R.string.error_save_api)
                    ErrorType.Retrieve -> getString(R.string.error_retrieve_api)
                },
                onOkayAction = {
                    // retry loading data
                    gitUsersViewModel.getUsersByQuery(binding.searchView.query.toString())
                }
            )
        }
    }

    companion object {
        const val ARG_USER = "user"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gitUsersViewModel.usersFromSearch.observe(viewLifecycleOwner, handleUsersFiltered)
        // setup search
        setupSearch()
        // setup RecyclerView
        binding.rvUsers.adapter = gitUsersAdapter
        binding.rvUsers.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.length > 1) {
                        gitUsersViewModel.getUsersByQuery(query)
                    }
                    binding.searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let {
                    if (it.length > 1) {
                        gitUsersViewModel.getUsersByQuery(query)
                    }
                }
                return true
            }
        })
    }


    private fun navigateToUserDetails(user: UserItem) {
        val action =
            GitUserListFragmentDirections.actionGitUserListFragmentToGitUserDetailsFragment(user)
        findNavController().navigate(action)
    }
}