package net.alexandroid.template2022.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.ActivityMainBinding
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logD
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private val navViewModel by viewModel<NavViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        mainViewModel.onActivityOnCreate()

        mainViewModel.uiState.collectIt(this) {
            logD("It: $it")
        }
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
        val appBarConfiguration = AppBarConfiguration(getListOfHomeDestinations())
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun getListOfHomeDestinations() = setOf(R.id.homeFragment)

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}