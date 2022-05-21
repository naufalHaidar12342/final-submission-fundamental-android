package xyz.heydarrn.discovergithubuser

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.heydarrn.discovergithubuser.databinding.FragmentDetailOfSelectedUserBinding
import xyz.heydarrn.discovergithubuser.model.TabLayoutAdapter
import xyz.heydarrn.discovergithubuser.viewmodel.DetailOfUserViewModel

class DetailOfSelectedUserFragment : Fragment() {
    private val args:DetailOfSelectedUserFragmentArgs by navArgs()
    private lateinit var receivedArgs:String
    private var _bindingDetail:FragmentDetailOfSelectedUserBinding?=null
    private val bindingDetail get() = _bindingDetail
    private lateinit var viewModelDetail:DetailOfUserViewModel


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
        val receivedID=args.idOfUsernameSelected
        viewModelDetail= ViewModelProvider(this)[DetailOfUserViewModel::class.java]

        setOptionMenuForDetailFragment()
        setUserDetail(receivedArgs, receivedID)
        openFollowerPage(receivedArgs)
        openFollowingPage(receivedArgs)

    }

    private fun setUserDetail(username:String, userID:Int){
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
                    if (it.name!=null){
                        fullnameUserDetail.text=it.name
                    }else {
                        fullnameUserDetail.text=resources.getString(R.string.fullname_got_null_response_template)
                    }

                    if (it.company!=null) {
                        companyUserDetail.text=it.company
                    }else {
                        companyUserDetail.text=resources.getString(R.string.company_got_null_response_template)
                    }

                    if (it.location!=null){
                        locationUserDetail.text=it.location
                    }else{
                        locationUserDetail.text=resources.getString(R.string.location_got_null_response_template)
                    }

                    repositoryUserDetail.text=resources.getString(R.string.repository_string_template,it.publicRepos.toString())
                }
            }
        }

        var isSwitched= false
        CoroutineScope(Dispatchers.IO).launch {
            val countUser=viewModelDetail.checkFavouriteUser(userID)
            withContext(Dispatchers.Main){
                if (countUser!=null){
                    if (countUser>0){
                        bindingDetail?.toggleButtonFavouriteUser?.isChecked=true
                        isSwitched=true
                    }else{
                        bindingDetail?.toggleButtonFavouriteUser?.isChecked=false
                        isSwitched=false
                    }
                }
            }
        }

        //remove user from favourite user database
        bindingDetail?.toggleButtonFavouriteUser?.setOnClickListener {
            isSwitched = ! isSwitched
            if (isSwitched){
                viewModelDetail.addToFavourite(username,userID)
            }else{
                viewModelDetail.removeFavouriteUserWithID(userID)
            }
            bindingDetail?.toggleButtonFavouriteUser?.isChecked=isSwitched
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

    private fun openFollowingPage(sendStringToFollowingFragment: String){
        bindingDetail?.buttonToFollowing?.setOnClickListener {
            findNavController().navigate(
                DetailOfSelectedUserFragmentDirections
                    .actionDetailOfSelectedUserFragmentToFollowingFragment()
                    .setUsernameToFollowing(sendStringToFollowingFragment))
        }
    }

    private fun setOptionMenuForDetailFragment(){
        bindingDetail?.toolbar3?.apply {
            val host:MenuHost=this
            host.addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.option_menu,menu)

                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    val option=when(menuItem.itemId){
                        R.id.theme_settings_option -> {
                            findNavController().navigate(DetailOfSelectedUserFragmentDirections.actionDetailOfSelectedUserFragmentToThemeSettingFragment())
                            true
                        }
                        R.id.favourite_user_option -> {
                            findNavController().navigate(DetailOfSelectedUserFragmentDirections.actionDetailOfSelectedUserFragmentToFavouriteUserFragment())
                            true
                        }
                        else -> false
                    }
                    return option
                }

            },viewLifecycleOwner,Lifecycle.State.RESUMED)

            //back to search
            setNavigationOnClickListener {
                findNavController().navigate(DetailOfSelectedUserFragmentDirections.actionDetailOfSelectedUserFragmentToSearchFragment())
            }
        }
    }



}