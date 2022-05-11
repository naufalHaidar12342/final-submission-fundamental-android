package xyz.heydarrn.discovergithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.discovergithubuser.model.api.ApiConfig
import xyz.heydarrn.discovergithubuser.model.api.FollowingOfUserResponse

class FollowingViewModel:ViewModel(){
    private var _showFollowingOfUser= MutableLiveData<ArrayList<FollowingOfUserResponse>>()
    private val showFollowingOfUser: LiveData<ArrayList<FollowingOfUserResponse>> = _showFollowingOfUser

    fun setFollowing(username:String){
        val followingClient=ApiConfig.getApiService().selectedUserFollowing(username)
        followingClient.enqueue(object : Callback<ArrayList<FollowingOfUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowingOfUserResponse>>,
                response: Response<ArrayList<FollowingOfUserResponse>>
            ) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()){
                    _showFollowingOfUser.value=response.body()
                }
                Log.d("FOLLOWING GOOD", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ArrayList<FollowingOfUserResponse>>, t: Throwable) {
                Log.d("FOLLOWING BAD", "onFailure: ${t.stackTrace}")
            }

        })
    }

    fun setNewFollowing():LiveData<ArrayList<FollowingOfUserResponse>> = showFollowingOfUser
}