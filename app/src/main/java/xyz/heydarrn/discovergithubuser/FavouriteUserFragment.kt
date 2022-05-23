package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.discovergithubuser.databinding.FragmentFavouriteUserBinding
import xyz.heydarrn.discovergithubuser.model.SearchUserListAdapter
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem
import xyz.heydarrn.discovergithubuser.model.room.FavouriteGithubUser
import xyz.heydarrn.discovergithubuser.viewmodel.FavouriteUserViewModel

class FavouriteUserFragment : Fragment() {
    private var _bindingFavoriteUser:FragmentFavouriteUserBinding?=null
    private val bindingFavoriteUser get() = _bindingFavoriteUser
    private val adapterFavouriteUser by lazy { SearchUserListAdapter() }
    private lateinit var viewModelFavourite:FavouriteUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindingFavoriteUser=FragmentFavouriteUserBinding.inflate(inflater,container,false)
        return bindingFavoriteUser?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFavourite=ViewModelProvider(this)[FavouriteUserViewModel::class.java]

        monitorFavouriteViewModel()
        setBackButtonActionWhenPressed()
    }

    private fun setBackButtonActionWhenPressed(){
        bindingFavoriteUser?.toolbarFavouriteUser?.setNavigationOnClickListener {
            findNavController().navigate(FavouriteUserFragmentDirections.actionFavouriteUserFragmentToSearchFragment())
        }
    }

    private fun monitorFavouriteViewModel(){
        bindingFavoriteUser?.recyclerViewFavouriteUser?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=LinearLayoutManager(context)
            this.adapter=adapterFavouriteUser
        }

        adapterFavouriteUser.setThisUserForSending(object : SearchUserListAdapter.ClickThisUser {
            override fun selectThisUser(
                selectedUser: String,
                idSelected: Int,
                profilePicture: String,
                profileLink: String
            ) {
                findNavController()
                    .navigate(FavouriteUserFragmentDirections.actionFavouriteUserFragmentToDetailOfSelectedUserFragment()
                        .setUsernameSelected(selectedUser)
                        .setIdOfUsernameSelected(idSelected)
                        .setAvatarOfUsernameSelected(profilePicture)
                        .setProfileOfUsernameSelected(profileLink))
            }

        })

        viewModelFavourite.getFavouriteUserFromDAO()?.observe(viewLifecycleOwner){
            if (it!=null){
                val mappingList= mappingListOfUser(it)
                adapterFavouriteUser.submitList(mappingList)
                false.showFavouriteLoading()
            }
        }
    }

    private fun mappingListOfUser(favouriteUser:List<FavouriteGithubUser>):ArrayList<ItemsItem>{
        val listFavourite=ArrayList<ItemsItem>()
        for (favourite in favouriteUser){
            val userMapped = ItemsItem(
                login = favourite.login,
                avatarUrl = favourite.avatarUrl,
                id = favourite.id,
                htmlUrl = favourite.htmlUrl
            )
            listFavourite.add(userMapped)
        }
        return listFavourite
    }

    private fun Boolean.showFavouriteLoading(){
        when(this){
            true -> bindingFavoriteUser?.progressBarFavouriteUser?.visibility=View.VISIBLE
            false -> bindingFavoriteUser?.progressBarFavouriteUser?.visibility=View.GONE
        }
    }


}