package xyz.heydarrn.discovergithubuser.model.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteUserDAO {
    @Insert
    suspend fun addThisUserToFavourite(favouriteGithubUser: FavouriteGithubUser)

    @Query("SELECT * FROM favourite_user")
    fun getAllFavouriteUser(): LiveData<List<FavouriteGithubUser>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id= :id")
    suspend fun checkIfUserFavourite(id: Int) : Int

    @Query("DELETE FROM favourite_user WHERE favourite_user.id= :id")
    suspend fun deleteThisUserFromFavourite(id: Int):Int
}