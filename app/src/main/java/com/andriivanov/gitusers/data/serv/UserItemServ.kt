package com.andriivanov.gitusers.data.serv

import com.google.gson.annotations.SerializedName

data class UserItemServ(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
)
