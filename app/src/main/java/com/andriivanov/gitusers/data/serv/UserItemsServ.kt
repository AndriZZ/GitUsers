package com.andriivanov.gitusers.data.serv


import com.google.gson.annotations.SerializedName

data class UserItemsServ(
    @SerializedName("items")
    val userItems: List<UserItemServ>?
)