package com.andriivanov.gitusers.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.andriivanov.gitusers.R
import com.andriivanov.gitusers.data.common.DataState
import com.andriivanov.gitusers.data.common.ErrorType
import com.andriivanov.gitusers.data.common.isLoading
import com.andriivanov.gitusers.data.common.onFailure
import com.andriivanov.gitusers.data.common.onSuccess
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.databinding.FragmentUserDetailsBinding
import com.andriivanov.gitusers.ui.GitUserListFragment.Companion.ARG_USER
import com.andriivanov.gitusers.utils.loadUrl
import com.andriivanov.gitusers.utils.parcelable
import com.andriivanov.gitusers.utils.showDialog
import com.andriivanov.gitusers.utils.visibleOrGone
import com.andriivanov.gitusers.viewmodel.GitUsersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class GitUserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private lateinit var user: UserItem
    private val gitUsersViewModel: GitUsersViewModel by viewModel()

    private val handleUserDetails = Observer<DataState<UserDetails>> { result ->
        binding.progressBar.visibleOrGone(result.isLoading())
        result.onSuccess { userDetails ->
            initViews(userDetails)
        }
        result.onFailure {
            showDialog(
                title = getString(R.string.error_general),
                message = it.errorMessage ?: when (it.errorType) {
                    ErrorType.Save -> getString(R.string.error_save_api)
                    ErrorType.Retrieve -> getString(R.string.error_retrieve_api)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().actionBar?.setHomeAsUpIndicator(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_back
            )
        )
        binding = FragmentUserDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gitUsersViewModel.userDetails.observe(viewLifecycleOwner, handleUserDetails)
        // Get user from safe args
        user = requireArguments().parcelable(ARG_USER)
            ?: throw IllegalStateException("User not found!")
        gitUsersViewModel.getUserDetails(user.login)
    }

    private fun initViews(userDetails: UserDetails) {
        binding.tvName.text = userDetails.name
        if (userDetails.email.isNotBlank()) {
            binding.tvEmail.text = userDetails.email
            binding.tvEmail.isVisible = true
        }
        binding.ivAvatar.loadUrl(userDetails.avatarUrl)
        binding.btnHireable.visibleOrGone(userDetails.hireable)
        binding.personalDetailsView.init(userDetails)
    }
}