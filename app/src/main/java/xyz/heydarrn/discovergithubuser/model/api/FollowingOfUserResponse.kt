package xyz.heydarrn.discovergithubuser.model.api

import com.google.gson.annotations.SerializedName

data class FollowingOfUserResponse(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("login")
    val login: String
)
