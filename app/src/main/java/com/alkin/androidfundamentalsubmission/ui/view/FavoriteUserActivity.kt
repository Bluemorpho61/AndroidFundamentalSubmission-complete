package com.alkin.androidfundamentalsubmission.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkin.androidfundamentalsubmission.adapter.FavAdapter
import com.alkin.androidfundamentalsubmission.data.local.helper.ViewModelFactory
import com.alkin.androidfundamentalsubmission.databinding.ActivityFavoriteUserBinding
import com.alkin.androidfundamentalsubmission.ui.model.FavoriteViewModel

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstanceWithotPref(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = FavAdapter(favoriteViewModel)
        val favoriteViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteViewModel.getFavorites().observe(this) { favList ->
            if (favList != null) {
                adapter.submitList(favList)
            }
        }

        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.adapter = adapter
    }

    private fun obtainViewModel(activity: FavoriteUserActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstanceWithotPref(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

}