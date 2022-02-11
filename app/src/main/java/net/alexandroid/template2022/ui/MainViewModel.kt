package net.alexandroid.template2022.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.utils.logD

class MainViewModel : BaseViewModel() {
    private val _state = MutableStateFlow("")
    val uiState: StateFlow<String> = _state

    fun onActivityOnCreate() {
        logD("${hashCode()}")
        _state.value = "Created"
    }
}