package com.alkin.androidfundamentalsubmission.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alkin.androidfundamentalsubmission.data.local.entity.UserEntity
import com.alkin.androidfundamentalsubmission.data.local.repository.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavorites(): LiveData<List<UserEntity>> = userRepository.getFavorites()

    fun setFavorites(user: UserEntity) = userRepository.setUserFavorites(user)

    fun deleteFavorite(user: UserEntity) =userRepository.removeUserFavorite(user)

    fun checkFavorite(user: UserEntity): LiveData<UserEntity> =
        userRepository.checkFavoriteByUsername(user)

}