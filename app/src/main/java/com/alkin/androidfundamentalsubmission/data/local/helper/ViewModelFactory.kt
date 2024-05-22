package com.alkin.androidfundamentalsubmission.data.local.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alkin.androidfundamentalsubmission.data.local.repository.UserRepository
import com.alkin.androidfundamentalsubmission.di.Injection
import com.alkin.androidfundamentalsubmission.ui.SettingPreferences
import com.alkin.androidfundamentalsubmission.ui.model.FavoriteViewModel
import com.alkin.androidfundamentalsubmission.ui.model.SettingViewModel

class ViewModelFactory private constructor(
    private val pref: SettingPreferences?,
    private val userRepository: UserRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            if (pref != null) {
                return SettingViewModel(pref) as T
            }
        }
        throw IllegalArgumentException("UNKNOWN VIEWMODEL CLASS: " + modelClass.name)
    }

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, pref: SettingPreferences): ViewModelFactory {
            val userRepository = Injection.provideRepository(context)
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(pref, userRepository)
            }.also { instance = it }
        }

        fun getInstanceWithotPref(context: Context): ViewModelFactory {
            val userRepository = Injection.provideRepository(context)
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(null, userRepository)
            }.also { instance = it }
        }
    }

}