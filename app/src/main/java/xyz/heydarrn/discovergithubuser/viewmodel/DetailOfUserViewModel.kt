package xyz.heydarrn.discovergithubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.discovergithubuser.model.api.ApiConfig
import xyz.heydarrn.discovergithubuser.model.api.UserDetailedInfoResponse
import xyz.heydarrn.discovergithubuser.model.room.FavouriteGithubUser
import xyz.heydarrn.discovergithubuser.model.room.FavouriteUserDAO
import xyz.heydarrn.discovergithubuser.model.room.FavouriteUserDatabase

class DetailOfUserViewModel(application: Application): AndroidViewModel(application),
    ViewModelProvider.Factory {
    private var _showUserDetail= MutableLiveData<UserDetailedInfoResponse>()
    private val showUserDetail: LiveData<UserDetailedInfoResponse> = _showUserDetail

    private var userDAO:FavouriteUserDAO?=null
    private var userDB:FavouriteUserDatabase?=null

    init {
        userDB=FavouriteUserDatabase.getFavouriteUserDatabase(application)
        userDAO=userDB?.favouriteUserDao()
    }

    fun setUserDetailInfo(selectedUser:String){
        val detailUser=ApiConfig.getApiService().getSelectedUserInfo(selectedUser)
        detailUser.enqueue(object : Callback<UserDetailedInfoResponse> {
            override fun onResponse(
                call: Call<UserDetailedInfoResponse>,
                response: Response<UserDetailedInfoResponse>
            ) {
                if (response.isSuccessful){
                    _showUserDetail.value=response.body()
                }
                Log.d("DETAIL USER SUCCESS", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<UserDetailedInfoResponse>, t: Throwable) {
                Log.d("DETAIL USER FAIL", "onFailure: ${t.stackTrace}")
            }

        })
    }

    fun setNewUserDetail():LiveData<UserDetailedInfoResponse> =showUserDetail

    fun addToFavourite(usernameSelected:String, id:Int, avatarURL:String, profileLink:String){
        CoroutineScope(Dispatchers.IO).launch {
            val favouriteUser= FavouriteGithubUser(
                login = usernameSelected,
                id = id,
                htmlUrl = profileLink,
                avatarUrl = avatarURL
            )

            userDAO?.addThisUserToFavourite(favouriteUser)
        }
    }

    suspend fun checkFavouriteUser(id: Int) = userDAO?.checkIfUserFavourite(id)

    fun removeFavouriteUserWithID(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDAO?.deleteThisUserFromFavourite(id)
        }
    }

}