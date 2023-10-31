package com.andriivanov.gitusers.data.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserItem(
    val avatarUrl: String,
    val id: Int,
    val login: String,
) : Parcelable