package xyz.heydarrn.discovergithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.discovergithubuser.model.api.ApiConfig
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem
import xyz.heydarrn.discovergithubuser.model.api.SearchUserResponse

class SearchUserViewModel : ViewModel() {
    private var _listOfSearchedUser= MutableLiveData<ArrayList<ItemsItem>>()
    private val listOfSearchedUser: LiveData<ArrayList<ItemsItem>> = _listOfSearchedUser

    fun searchUserOnSubmittedText(findUser:String){
        val client= ApiConfig.getApiService().searchForUser(findUser)
        client.enqueue(object : Callback<SearchUserResponse> {

            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                if (response.isSuccessful){
                    /*if successfully got response, insert the response body to arrayList "items"*/
                    _listOfSearchedUser.value= response.body()?.items
                }else{
                    Log.d("success get response", "onResponse: ${response.body()}")
                }

            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                Log.d("fail to get response", "onFailure: ${t.stackTrace}")
            }

        })
    }

    fun setResultForViewModel(): LiveData<ArrayList<ItemsItem>> = listOfSearchedUser
}