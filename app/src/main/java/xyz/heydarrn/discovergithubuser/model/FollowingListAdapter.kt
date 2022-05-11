package xyz.heydarrn.discovergithubuser.model

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.discovergithubuser.R
import xyz.heydarrn.discovergithubuser.databinding.GithubUserCardBinding
import xyz.heydarrn.discovergithubuser.model.api.FollowingOfUserResponse

class FollowingListAdapter:ListAdapter<FollowingOfUserResponse,FollowingListAdapter.FollowingViewHolder>(FollowingDiffUtilCallback()) {
    class FollowingViewHolder(private val bindingUserCardBinding: GithubUserCardBinding):RecyclerView.ViewHolder(bindingUserCardBinding.root) {
        fun bindFollowing(following: FollowingOfUserResponse){
            bindingUserCardBinding.apply {
                Glide.with(itemView)
                    .load(following.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .circleCrop()
                    .into(profilePicsUserCard)

                usernameUserCard.text=itemView.resources.getString(R.string.username_template,following.login)
                buttonVisitProfile.setOnClickListener {
                    itemView.context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(following.htmlUrl))
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view=GithubUserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bindFollowing(getItem(position))
    }
}