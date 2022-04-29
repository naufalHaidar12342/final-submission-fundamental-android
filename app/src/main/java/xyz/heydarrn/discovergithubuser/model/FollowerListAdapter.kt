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
import xyz.heydarrn.discovergithubuser.model.api.FollowerOfUserResponse

class FollowerListAdapter:ListAdapter<FollowerOfUserResponse,FollowerListAdapter.FollowerViewHolder>(FollowerDiffUtilCallback()) {
    class FollowerViewHolder(private val bindingUserCardBinding: GithubUserCardBinding):RecyclerView.ViewHolder(bindingUserCardBinding.root) {
        fun bindFollower(follower:FollowerOfUserResponse){
            bindingUserCardBinding.apply {
                Glide.with(itemView)
                    .load(follower.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .circleCrop()
                    .into(profilePicsUserCard)

                usernameUserCard.text=itemView.resources.getString(R.string.username_template,follower.login)
                buttonVisitProfile.setOnClickListener {
                    val intent=Intent(Intent.ACTION_VIEW, Uri.parse(follower.htmlUrl))
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view=GithubUserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bindFollower(getItem(position))
    }
}