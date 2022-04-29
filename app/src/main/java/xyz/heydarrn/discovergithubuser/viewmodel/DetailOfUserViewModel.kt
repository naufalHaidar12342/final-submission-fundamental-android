package xyz.heydarrn.discovergithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.discovergithubuser.model.api.ApiConfig
import xyz.heydarrn.discovergithubuser.model.api.UserDetailedInfoResponse

class DetailOfUserViewModel: ViewModel() {
    private var _showUserDetail= MutableLiveData<UserDetailedInfoResponse>()
    private val showUserDetail: LiveData<UserDetailedInfoResponse> = _showUserDetail

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
}