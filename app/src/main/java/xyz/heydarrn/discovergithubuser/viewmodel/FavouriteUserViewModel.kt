package xyz.heydarrn.discovergithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.heydarrn.discovergithubuser.model.room.FavouriteGithubUser
import xyz.heydarrn.discovergithubuser.model.room.FavouriteUserDAO
import xyz.heydarrn.discovergithubuser.model.room.FavouriteUserDatabase

class FavouriteUserViewModel (application: Application): AndroidViewModel(application) {

    private var favouriteUserDAO:FavouriteUserDAO?
    private var favouriteUserDb:FavouriteUserDatabase?

    init {
        favouriteUserDb=FavouriteUserDatabase.getFavouriteUserDatabase(application)
        favouriteUserDAO=favouriteUserDb?.favouriteUserDao()
    }

    fun getFavouriteUserFromDAO() :LiveData<List<FavouriteGithubUser>>?{
        return favouriteUserDAO?.getAllFavouriteUser()
    }
}