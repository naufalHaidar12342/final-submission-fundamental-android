package xyz.heydarrn.discovergithubuser.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.discovergithubuser.FollowerFragment
import xyz.heydarrn.discovergithubuser.FollowingFragment

class TabLayoutAdapter(fragment:FragmentActivity, bundleFollower: Bundle):FragmentStateAdapter(fragment) {
    private var bundle=bundleFollower

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragments:Fragment?=null
        when(position){
            0 ->{ fragments=FollowerFragment() }
            1 ->{ fragments=FollowingFragment() }
        }
        fragments?.arguments=this.bundle
        return fragments as Fragment
    }
}