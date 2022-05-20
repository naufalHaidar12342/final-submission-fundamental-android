package xyz.heydarrn.discovergithubuser.model

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.discovergithubuser.R
import xyz.heydarrn.discovergithubuser.databinding.GithubUserCardBinding
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem
import xyz.heydarrn.discovergithubuser.model.api.SearchUserResponse

class SearchUserListAdapter:ListAdapter<ItemsItem,SearchUserListAdapter.SearchViewHolder>(SearchUserDiffUtilCallback()) {
    var whenUserSelected: ClickThisUser?=null

    fun setThisUserForSending(thisSelectedUser:ClickThisUser){ this.whenUserSelected=thisSelectedUser }

    inner class SearchViewHolder(private val bindingUser:GithubUserCardBinding):RecyclerView.ViewHolder(bindingUser.root) {
        fun bindDataToCard(item:ItemsItem){
            bindingUser.apply {
                Glide.with(itemView)
                    .load(item.avatarUrl)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .circleCrop()
                    .into(profilePicsUserCard)

                usernameUserCard.text=itemView.resources.getString(R.string.username_template,item.login)

                buttonVisitProfile.setOnClickListener {
                    itemView.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl))
                    )
                }
                root.setOnClickListener {
                    whenUserSelected?.selectThisUser(item.login, item.id)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view=GithubUserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindDataToCard(getItem(position))
    }

    interface ClickThisUser{
        fun selectThisUser(selectedUser:String, idSelected:Int)
    }
}