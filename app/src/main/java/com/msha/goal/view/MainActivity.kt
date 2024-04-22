package com.msha.goal.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.msha.goal.viewmodel.MainViewModel
import com.msha.goal.R
import com.msha.goal.databinding.ActivityMainBinding
import com.msha.goal.model.Goal
import com.msha.goal.model.GoalDao
import com.msha.goal.model.GoalDatabase
import com.msha.goal.repository.GoalRepository
import com.msha.goal.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val application = requireNotNull(this).application
        val dao : GoalDao = GoalDatabase.getInstance(application).goalDao
        val repository = GoalRepository(dao)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}