package com.andriivanov.gitusers.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.andriivanov.gitusers.data.post.UserItem
import com.andriivanov.gitusers.databinding.ItemUserBinding
import com.andriivanov.gitusers.ui.recyclerview.viewholder.UsersViewHolder

class GitUsersAdapter(
    private val onGitUserClick: (item: UserItem) -> Unit
) : ListAdapter<UserItem, UsersViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binder)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(currentList[position], onGitUserClick)
    }

    override fun getItemCount(): Int = currentList.size

    companion object {

        private val DIFFER = object : DiffUtil.ItemCallback<UserItem>() {

            override fun areItemsTheSame(
                oldItem: UserItem,
                newItem: UserItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserItem,
                newItem: UserItem
            ): Boolean = oldItem == newItem

        }
    }
}