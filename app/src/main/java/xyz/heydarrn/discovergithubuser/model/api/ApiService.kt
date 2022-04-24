package xyz.heydarrn.discovergithubuser.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.heydarrn.discovergithubuser.BuildConfig

interface ApiService {
    @Headers(GITHUB_TOKEN)
    @GET("search/users")
    fun searchForUser(@Query("q") searchThisUser:String) : Call<SearchUserResponse>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}")
    fun getSelectedUserInfo(@Path("username") showUsernameInfo:String) :Call<UserDetailedInfoResponse>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}/followers")
    fun selectedUserFollower(@Path("username") showThisUserFollower:String):Call<ArrayList<FollowerOfUserResponse>>

    @Headers(GITHUB_TOKEN)
    @GET("users/{username}/following")
    fun selectedUserFollowing(@Path("username") showThisUserFollowing: String) : Call<ArrayList<FollowingOfUserResponse>>
    companion object{
        private const val GITHUB_TOKEN=BuildConfig.PERSONAL_ACCESS_TOKEN
    }

}