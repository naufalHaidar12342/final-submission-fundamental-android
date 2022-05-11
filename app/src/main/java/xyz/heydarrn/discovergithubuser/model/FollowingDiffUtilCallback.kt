package xyz.heydarrn.discovergithubuser.model

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.discovergithubuser.model.api.FollowingOfUserResponse

class FollowingDiffUtilCallback:DiffUtil.ItemCallback<FollowingOfUserResponse>() {
    override fun areItemsTheSame(
        oldItem: FollowingOfUserResponse,
        newItem: FollowingOfUserResponse
    ): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(
        oldItem: FollowingOfUserResponse,
        newItem: FollowingOfUserResponse
    ): Boolean {
        return oldItem == newItem
    }
}