package com.andriivanov.gitusers.data.post

import com.google.gson.annotations.SerializedName


data class UserDetails(
    val id: Int,
    val name: String,
    val email: String,
    val blog: String,
    val company: String,
    val location: String,
    val hireable: Boolean,
    val bio: String,
    val avatarUrl: String,
    val following: Int,
    val followers: Int,
    val repos: Int,
    val privateRepos: Int,
)