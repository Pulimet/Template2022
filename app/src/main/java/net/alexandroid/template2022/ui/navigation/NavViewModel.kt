package net.alexandroid.template2022.ui.navigation

import android.content.Intent
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel

class NavViewModel : BaseViewModel() {
    // Navigation Support
    private val _navigate = MutableStateFlow(NavParams())
    val getChangeNavigation: StateFlow<NavParams> = _navigate
    private val _navigateUp = MutableStateFlow(false)
    val getNavigateUp: StateFlow<Boolean> = _navigateUp

    fun navigateTo(navDirections: NavDirections, extras: FragmentNavigator.Extras? = null) {
        _navigate.value = NavParams(navDirections, extras)
        _navigate.value = NavParams() // Allows future navigation
    }

    fun navigateUp() {
        _navigateUp.value = true
        _navigateUp.value = false
    }

    // Change Activity Support
    private val _startActivity = MutableStateFlow(IntentParams())
    val getStartActivity: StateFlow<IntentParams> = _startActivity

    fun startActivity(clazz: Class<*>) {
        _startActivity.value = IntentParams(clazz, Intent(), false)
        _startActivity.value = IntentParams()
    }
}