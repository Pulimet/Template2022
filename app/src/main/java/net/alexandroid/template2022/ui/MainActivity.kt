package net.alexandroid.template2022.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.ActivityMainBinding
import net.alexandroid.template2022.ui.navigation.NavObserver
import net.alexandroid.template2022.ui.navigation.NavViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), NavObserver.Provider {
    private lateinit var binding: ActivityMainBinding
    private val navViewModel: NavViewModel by viewModel()
    private val navObserver: NavObserver by inject { parametersOf(this) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        navObserver.observe()
    }

    private fun setViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setupNavigationUi()
    }

    private fun setupNavigationUi() {
        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navObserver.getListOfHomeDestinations())
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()

    // NavObserver.Provider
    override fun provideActivity() = this
    override fun provideNavController() = navController
    override fun provideNavViewModel() = navViewModel
}