package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
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

        setUserDetail(receivedArgs)
        openFollowerPage(receivedArgs)
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

    private fun openFollowerPage(sendStringToFollowerFragment: String){
        bindingDetail?.buttonToFollower?.setOnClickListener {
            findNavController().navigate(
                DetailOfSelectedUserFragmentDirections
                    .actionDetailOfSelectedUserFragmentToFollowerFragment()
                    .setUsernameToFollower(sendStringToFollowerFragment))
        }
    }

}