package com.andriivanov.gitusers.data.serv


import com.google.gson.annotations.SerializedName

data class UserDetailsServ(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("blog")
    val blog: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("hireable")
    val hireable: Boolean?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("public_repos")
    val repos: Int?,
    @SerializedName("total_private_repos")
    val privateRepos: Int?,
)