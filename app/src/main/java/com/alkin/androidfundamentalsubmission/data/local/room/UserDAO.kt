package com.alkin.androidfundamentalsubmission.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alkin.androidfundamentalsubmission.data.local.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    fun getUser():LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(username: UserEntity)

    @Delete
    fun removeFavorite(username: UserEntity)

    @Query("SELECT * FROM user WHERE username =:username")
    fun checkFavoriteByUsername(username: String):LiveData<UserEntity>
}