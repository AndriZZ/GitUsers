package com.andriivanov.gitusers.ui.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.databinding.ItemUserBinding
import com.andriivanov.gitusers.utils.loadUrl


class UsersViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: UserItem,
        onGitUserClick: (item: UserItem) -> Unit
    ) = with(binding) {
        tvName.text = item.login
        ivAvatar.loadUrl(item.avatarUrl)
        root.setOnClickListener {
            onGitUserClick(item)
        }
    }
}