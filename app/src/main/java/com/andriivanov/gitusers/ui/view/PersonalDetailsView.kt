package com.andriivanov.gitusers.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.andriivanov.gitusers.R
import com.andriivanov.gitusers.data.post.UserDetails
import com.andriivanov.gitusers.databinding.ItemPersonalDetailBinding

class PersonalDetailsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ItemPersonalDetailBinding =
        ItemPersonalDetailBinding.inflate(LayoutInflater.from(context), this)

    fun init(userDetails: UserDetails) {
        binding.tvBio.text =
            userDetails.bio.ifBlank { context.getString(R.string.lorem_ipsum_long) }
        if (userDetails.company.isNotBlank()) binding.tvCompanyName.text = userDetails.company
        if (userDetails.location.isNotBlank()) binding.tvLocation.text = userDetails.location
        binding.tvFollowers.text = context.getString(R.string.followers, userDetails.followers)
        binding.tvFollowing.text = context.getString(R.string.following, userDetails.following)
        binding.tvRepos.text = context.getString(R.string.repos, userDetails.repos)
    }

}