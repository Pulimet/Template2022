package net.alexandroid.template2022.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import net.alexandroid.template2022.R
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logs.logE

class NavObserver(private val provider: Provider) {

    fun getListOfHomeDestinations() = setOf(R.id.homeFragment)

    fun observe() {
        observeFragmentNavigation()
        observeActivityNavigation()
    }

    private fun observeFragmentNavigation() {
        provider.provideNavViewModel().apply {
            getChangeNavigation.collectIt(provider.provideActivity()) { navParams ->
                try {
                    navigateTo(navParams)
                } catch (e: IllegalArgumentException) {
                    logE(t = e)
                }
            }
            getNavigateUp.collectIt(provider.provideActivity()) {
                provider.provideNavController().navigateUp()
            }
        }
    }

    private fun navigateTo(navParams: NavParams) {
        if (navParams.navDirections == null) return
        provider.provideNavController().navigate(
            navParams.navDirections.actionId,
            navParams.navDirections.arguments,
            navParams.navOptions,
            navParams.extras
        )
    }

    private fun observeActivityNavigation() {
        provider.provideNavViewModel().getStartActivity.collectIt(provider.provideActivity()) { intentParams ->
            try {
                startActivity(intentParams)
            } catch (e: Exception) {
                logE(t = e)
            }
        }
    }

    private fun startActivity(intentParams: IntentParams) {
        if (intentParams.intent == null) return
        provider.provideActivity().apply {
            intentParams.clazz?.let { clazz ->
                intentParams.intent.setClass(this, clazz)
            }
            startActivity(intentParams.intent)
            if (intentParams.finish) finish()
        }
    }


    interface Provider {
        fun provideActivity(): AppCompatActivity
        fun provideNavController(): NavController
        fun provideNavViewModel(): NavViewModel
    }
}