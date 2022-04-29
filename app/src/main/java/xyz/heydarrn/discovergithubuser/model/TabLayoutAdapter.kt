package xyz.heydarrn.discovergithubuser.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.heydarrn.discovergithubuser.FollowerFragment
import xyz.heydarrn.discovergithubuser.FollowingFragment

class TabLayoutAdapter(fragment:FragmentActivity):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragments:Fragment?=null
        when(position){
            0 ->{ fragments=FollowerFragment() }
            1 ->{ fragments=FollowingFragment() }
        }
        return fragments as Fragment
    }
}