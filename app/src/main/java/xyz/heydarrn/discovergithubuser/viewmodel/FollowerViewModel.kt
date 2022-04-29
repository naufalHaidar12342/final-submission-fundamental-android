package xyz.heydarrn.discovergithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.discovergithubuser.model.api.ApiConfig
import xyz.heydarrn.discovergithubuser.model.api.FollowerOfUserResponse

class FollowerViewModel:ViewModel() {
    private var _showFollowerOfUser= MutableLiveData<ArrayList<FollowerOfUserResponse>>()
    private val showFollowerOfUser: LiveData<ArrayList<FollowerOfUserResponse>> = _showFollowerOfUser
    
    fun setFollower(userWithFollower:String){
        val followerClient=ApiConfig.getApiService().selectedUserFollower(userWithFollower)
        followerClient.enqueue(object : Callback<ArrayList<FollowerOfUserResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowerOfUserResponse>>,
                response: Response<ArrayList<FollowerOfUserResponse>>
            ) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()){
                    _showFollowerOfUser.postValue(response.body())
                }
                Log.d("FOLLOWER GOOD", "onResponse: ${response.body()}")

            }

            override fun onFailure(call: Call<ArrayList<FollowerOfUserResponse>>, t: Throwable) {
                Log.d("FOLLOWER BAD", "onFailure: ${t.cause}")
            }

        })
    }

    fun setNewFollower():LiveData<ArrayList<FollowerOfUserResponse>> = showFollowerOfUser
}