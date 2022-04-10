package net.alexandroid.template2022.ui.api.add

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD

class ApiAddViewModel : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    fun onAddParamBtnClick() {
        _showDialog.value = true
        _showDialog.value = false
    }

    fun onNewParamSubmit(key: String, value: String) {
        logD("Key: $key, Value: $value")
    }

    fun onSaveBtnClick() {
        logD()
    }

    fun onImportBtnClick() {
        logD()
    }
}