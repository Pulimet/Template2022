package net.alexandroid.template2022.ui.navigation

import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel

class NavViewModel : BaseViewModel() {
    private val _navigate = MutableStateFlow(NavParams())
    val navigation: StateFlow<NavParams> = _navigate

    fun navigateTo(navDirections: NavDirections) {
        _navigate.value = NavParams(navDirections)
        _navigate.value = NavParams() // Allows future navigation
    }
}