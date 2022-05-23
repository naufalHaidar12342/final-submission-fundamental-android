package xyz.heydarrn.discovergithubuser.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteGithubUser::class], version = 2, exportSchema = false)
abstract class FavouriteUserDatabase : RoomDatabase() {
    companion object{
        var DATABASE_INSTANCE:FavouriteUserDatabase?=null

        fun getFavouriteUserDatabase(context: Context) : FavouriteUserDatabase?{
            if (DATABASE_INSTANCE==null){
                DATABASE_INSTANCE=Room.databaseBuilder(context.applicationContext,FavouriteUserDatabase::class.java,"favourite_user_db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DATABASE_INSTANCE
        }
    }

    abstract fun favouriteUserDao():FavouriteUserDAO
}