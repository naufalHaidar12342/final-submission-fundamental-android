package xyz.heydarrn.discovergithubuser.model

import androidx.recyclerview.widget.DiffUtil
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem

class SearchUserDiffUtilCallback:DiffUtil.ItemCallback<ItemsItem>() {
    override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
        return oldItem == newItem
    }

}