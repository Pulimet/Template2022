package net.alexandroid.template2022.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.ActivityMainBinding
import net.alexandroid.template2022.ui.navigation.IntentParams
import net.alexandroid.template2022.ui.navigation.NavParams
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logE
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private val navViewModel by viewModel<NavViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        observeFragmentNavigation()
        observeActivityNavigation()
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

    private fun observeFragmentNavigation() {
        navViewModel.getChangeFragment.collectIt(this) { navParams ->
            try {
                navigateToFragment(navParams)
            } catch (e: IllegalArgumentException) {
                logE(t = e)
            }
        }
    }

    private fun navigateToFragment(navParams: NavParams) {
        if (navParams.navDirections == null) return
        navController.navigate(
            navParams.navDirections.actionId,
            navParams.navDirections.arguments,
            navParams.navOptions,
            navParams.extras
        )
    }

    private fun observeActivityNavigation() {
        navViewModel.getStartActivity.collectIt(this) { intentParams ->
            try {
                startActivity(intentParams)
            } catch (e: Exception) {
                logE(t = e)
            }
        }
    }

    private fun startActivity(intentParams: IntentParams) {
        if (intentParams.clazz == null) return
        // TODO Add support for just Intent
        intentParams.clazz?.let {
            intentParams.intent.setClass(this, intentParams.clazz)
        }
        startActivity(intentParams.intent)
        if (intentParams.finish) finish()
    }

    private fun getListOfHomeDestinations() = setOf(R.id.homeFragment)

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()
}