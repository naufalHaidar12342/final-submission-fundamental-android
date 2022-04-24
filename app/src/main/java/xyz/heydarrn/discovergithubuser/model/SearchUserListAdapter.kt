package xyz.heydarrn.discovergithubuser.model

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.heydarrn.discovergithubuser.databinding.GithubUserCardBinding
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem
import xyz.heydarrn.discovergithubuser.model.api.SearchUserResponse

class SearchUserListAdapter():ListAdapter<ItemsItem,SearchUserListAdapter.SearchViewHolder>(SearchUserDiffUtilCallback()) {
    class SearchViewHolder(val bindingUser:GithubUserCardBinding):RecyclerView.ViewHolder(bindingUser.root) {
        fun bindDataToCard(item:ItemsItem){
            bindingUser.apply {
                //
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}