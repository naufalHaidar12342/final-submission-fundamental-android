package xyz.heydarrn.discovergithubuser.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favourite_user")
data class FavouriteGithubUser(
    val login:String,

    @PrimaryKey
    val id:Int,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl:String,

    @ColumnInfo(name = "html_url")
    val htmlUrl:String

) : Serializable
