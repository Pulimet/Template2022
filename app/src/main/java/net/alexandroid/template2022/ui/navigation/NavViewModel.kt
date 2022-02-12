package net.alexandroid.template2022.ui.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel

class NavViewModel : BaseViewModel() {
    // Fragment Navigation Support
    private val _navigate = MutableStateFlow(NavParams())
    val getChangeFragment: StateFlow<NavParams> = _navigate

    fun navigateTo(navDirections: NavDirections) {
        _navigate.value = NavParams(navDirections)
        _navigate.value = NavParams() // Allows future navigation
    }

    // Change Activity Support
    private val _startActivity = MutableStateFlow(IntentParams())
    val getStartActivity: StateFlow<IntentParams> = _startActivity

    fun startActivity(clazz: Class<*>) {
        _startActivity.value = IntentParams(clazz, finish = false)
        _startActivity.value = IntentParams()
    }
}