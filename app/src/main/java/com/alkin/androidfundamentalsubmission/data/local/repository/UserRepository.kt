package com.alkin.androidfundamentalsubmission.data.local.repository

import androidx.lifecycle.LiveData
import com.alkin.androidfundamentalsubmission.data.local.entity.UserEntity
import com.alkin.androidfundamentalsubmission.data.local.room.UserDAO
import com.alkin.androidfundamentalsubmission.utils.AppExecutor

class UserRepository private constructor(
    private val userDAO: UserDAO,
    private val appExecutor: AppExecutor,
) {
    fun getFavorites(): LiveData<List<UserEntity>> = userDAO.getUser()

    fun setUserFavorites(user: UserEntity) =
        appExecutor.diskIO.execute {
            userDAO.addFavorite(user)
        }

    fun removeUserFavorite(user: UserEntity) =
        appExecutor.diskIO.execute {
            userDAO.removeFavorite(user)
        }

    fun checkFavoriteByUsername(user: UserEntity): LiveData<UserEntity> =
        userDAO.checkFavoriteByUsername(user.username)

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(
            userDAO: UserDAO,
            executor: AppExecutor,

            ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userDAO, executor)
        }.also { instance = it }
    }
}

