package xyz.heydarrn.discovergithubuser.model.api

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: ArrayList<ItemsItem>
)

data class ItemsItem(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("login")
    val login: String
)