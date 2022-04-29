package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import xyz.heydarrn.discovergithubuser.databinding.FragmentDetailOfSelectedUserBinding
import xyz.heydarrn.discovergithubuser.model.TabLayoutAdapter
import xyz.heydarrn.discovergithubuser.viewmodel.DetailOfUserViewModel

class DetailOfSelectedUserFragment : Fragment() {
    private val args:DetailOfSelectedUserFragmentArgs by navArgs()
    private lateinit var receivedArgs:String
    private var _bindingDetail:FragmentDetailOfSelectedUserBinding?=null
    private val bindingDetail get() = _bindingDetail
    private val viewModelDetail by viewModels<DetailOfUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingDetail= FragmentDetailOfSelectedUserBinding.inflate(inflater,container,false)
        return bindingDetail?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receivedArgs=args.usernameSelected
        Log.d("CHECK ARGS", "onViewCreated: $receivedArgs")
        setTabLayout()
        setUserDetail(receivedArgs)
    }

    private fun setUserDetail(username:String){
        viewModelDetail.setUserDetailInfo(username)
        viewModelDetail.setNewUserDetail().observe(viewLifecycleOwner){
            if (it!=null){
                bindingDetail?.apply {
                    Glide.with(requireActivity())
                        .load(it.avatarUrl)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .circleCrop()
                        .into(profilePicsUserDetail)
                    usernameUserDetail.text=resources.getString(R.string.username_template,it.login)
                    if (it.name!=null){ fullnameUserDetail.text=it.name}
                    else { fullnameUserDetail.text=resources.getString(R.string.fullname_got_null_response_template)}

                    if (it.company!=null) companyUserDetail.text=it.company
                    else companyUserDetail.text=resources.getString(R.string.company_got_null_response_template)

                    repositoryUserDetail.text=resources.getString(R.string.repository_string_template,it.publicRepos.toString())
                }
            }
        }
    }
    private fun setTabLayout(){
        val tabSection=TabLayoutAdapter(requireActivity())
        val viewPagers: ViewPager2 = bindingDetail?.viewPagers2UserDetail!!
        viewPagers.adapter=tabSection

        val tabs: TabLayout = bindingDetail!!.tabsCollapsible
        TabLayoutMediator(tabs,viewPagers) {tab, position ->
            tab.text=resources.getString(TAB_NAMES[position])
        }.attach()
    }

    companion object {
        private val TAB_NAMES= intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}