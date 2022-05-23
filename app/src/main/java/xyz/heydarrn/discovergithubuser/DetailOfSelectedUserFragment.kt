package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.heydarrn.discovergithubuser.databinding.FragmentDetailOfSelectedUserBinding
import xyz.heydarrn.discovergithubuser.viewmodel.DetailOfUserViewModel

class DetailOfSelectedUserFragment : Fragment() {
    private val args:DetailOfSelectedUserFragmentArgs by navArgs()
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
        viewModelDetail= ViewModelProvider(this)[DetailOfUserViewModel::class.java]

        //receive safe Arguments, sent from search fragment, following, and follower
        val receivedUsername = args.usernameSelected
        val receivedID=args.idOfUsernameSelected
        val receivedAvatarURL=args.avatarOfUsernameSelected
        val receivedHtmlURL=args.profileOfUsernameSelected

        setOptionMenuForDetailFragment()
        setUserDetail(receivedUsername, receivedID, receivedAvatarURL, receivedHtmlURL)
        openFollowerPage(receivedUsername,receivedID,receivedAvatarURL,receivedHtmlURL)
        openFollowingPage(receivedUsername,receivedID, receivedAvatarURL, receivedHtmlURL)

    }

    private fun setUserDetail(username:String, userID:Int, userAvatar: String, userProfileLink: String){
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

        //add or remove user from favourite user database
        bindingDetail?.toggleButtonFavouriteUser?.setOnClickListener {
            isSwitched = ! isSwitched
            if (isSwitched){
                viewModelDetail.addToFavourite(username,userID,userAvatar,userProfileLink)
            }else{
                viewModelDetail.removeFavouriteUserWithID(userID)
            }
            bindingDetail?.toggleButtonFavouriteUser?.isChecked=isSwitched
        }
    }

    private fun openFollowerPage(sendUsernameToFollower: String, sendUsersID:Int, sendAvatar:String, sendProfileURL:String){
        bindingDetail?.buttonToFollower?.setOnClickListener {
            findNavController().navigate(
                DetailOfSelectedUserFragmentDirections
                    .actionDetailOfSelectedUserFragmentToFollowerFragment()
                    .setUsernameToFollower(sendUsernameToFollower)
                    .setUsersIdToFollower(sendUsersID)
                    .setUsersAvatarToFollower(sendAvatar)
                    .setUsersProfileLinkToFollower(sendProfileURL))
        }
    }

    private fun openFollowingPage(sendUsernameToFollowing: String, usersID:Int, usersAvatar:String, usersProfileLink:String){
        bindingDetail?.buttonToFollowing?.setOnClickListener {
            findNavController().navigate(
                DetailOfSelectedUserFragmentDirections
                    .actionDetailOfSelectedUserFragmentToFollowingFragment()
                    .setUsernameToFollowing(sendUsernameToFollowing)
                    .setUsersIdToFollowing(usersID)
                    .setUsersAvatarToFollowing(usersAvatar)
                    .setUsersProfileLinkToFollowing(usersProfileLink))
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