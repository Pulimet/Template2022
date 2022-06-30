package net.alexandroid.template2022.ui.navigation

import android.content.Intent
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.utils.emitSharedFlow

class NavViewModel : BaseViewModel() {
    // Navigation Support
    private val _navigate = MutableSharedFlow<NavParams>()
    val getChangeNavigation = _navigate.asSharedFlow()
    private val _navigateUp = MutableSharedFlow<Unit>()
    val getNavigateUp = _navigateUp.asSharedFlow()

    fun navigateTo(navDirections: NavDirections, extras: FragmentNavigator.Extras? = null) {
        viewModelScope.launch { _navigate.emit(NavParams(navDirections, extras)) }
    }

    fun navigateUp() {
        emitSharedFlow(_navigateUp)
    }

    // Change Activity Support
    private val _startActivity = MutableSharedFlow<IntentParams>()
    val getStartActivity = _startActivity.asSharedFlow()

    fun startActivity(clazz: Class<*>) {
        viewModelScope.launch { _startActivity.emit(IntentParams(clazz, Intent(), false)) }
    }
}