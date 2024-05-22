package com.alkin.androidfundamentalsubmission.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alkin.androidfundamentalsubmission.R
import com.alkin.androidfundamentalsubmission.adapter.UserAdapter
import com.alkin.androidfundamentalsubmission.data.local.helper.ViewModelFactory
import com.alkin.androidfundamentalsubmission.databinding.ActivityMainBinding
import com.alkin.androidfundamentalsubmission.ui.SettingPreferences
import com.alkin.androidfundamentalsubmission.ui.datastore
import com.alkin.androidfundamentalsubmission.ui.model.MainViewModel
import com.alkin.androidfundamentalsubmission.ui.model.SettingViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBar.left, systemBar.top, systemBar.right, systemBar.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        with(binding) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                queryHint = "Cari username github disini"
                this.setOnQueryTextListener(object : OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {
                        mainViewModel.findUsers(query)
                        return false
                    }
                })
            }
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val pref = SettingPreferences.getInstance(application.datastore)
        val factory = ViewModelFactory.getInstance(this, pref)
        val settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        mainViewModel.githubUserData.observe(this) {
            binding.rvHasil.layoutManager = LinearLayoutManager(this)
            val adapter = UserAdapter()
            adapter.submitList(it)
            binding.rvHasil.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_pengaturan -> {
                val toSetting = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(toSetting)
            }

            R.id.menu_favorit -> {
                val toFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(toFavorite)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvHasil.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvHasil.visibility = View.VISIBLE
        }
    }

}