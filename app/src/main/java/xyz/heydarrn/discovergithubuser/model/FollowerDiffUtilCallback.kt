package xyz.heydarrn.discovergithubuser.model

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.discovergithubuser.model.api.FollowerOfUserResponse

class FollowerDiffUtilCallback:DiffUtil.ItemCallback<FollowerOfUserResponse>() {
    override fun areItemsTheSame(
        oldItem: FollowerOfUserResponse,
        newItem: FollowerOfUserResponse
    ): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(
        oldItem: FollowerOfUserResponse,
        newItem: FollowerOfUserResponse
    ): Boolean {
        return oldItem == newItem
    }
}