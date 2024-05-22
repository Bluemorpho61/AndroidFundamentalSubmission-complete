package com.alkin.androidfundamentalsubmission.di

import android.content.Context
import com.alkin.androidfundamentalsubmission.data.local.repository.UserRepository
import com.alkin.androidfundamentalsubmission.data.local.room.UserDatabase
import com.alkin.androidfundamentalsubmission.data.remote.retrofit.ApiConfig
import com.alkin.androidfundamentalsubmission.utils.AppExecutor

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiServices()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDAO()
        val appExecutors = AppExecutor()
        return UserRepository.getInstance(dao, appExecutors)
    }
}