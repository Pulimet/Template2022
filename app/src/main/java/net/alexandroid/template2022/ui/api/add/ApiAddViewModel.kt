package net.alexandroid.template2022.ui.api.add

import android.util.Patterns
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.R
import net.alexandroid.template2022.ui.api.add.recycler.Param
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD

class ApiAddViewModel : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private fun showDialog() {
        _showDialog.value = true
        _showDialog.value = false
    }

    private val _addBtnEnabled = MutableStateFlow(false)
    val addBtnState: StateFlow<Boolean> = _addBtnEnabled

    private val _saveBtnEnabled = MutableStateFlow(false)
    val saveBtnState: StateFlow<Boolean> = _saveBtnEnabled

    private val _baseUrlError = MutableStateFlow(0)
    val baseUrlError: StateFlow<Int> = _baseUrlError

    private val _showToast = MutableStateFlow(0)
    val showToast: StateFlow<Int> = _showToast

    private fun showToast(resId: Int) {
        _showToast.value = resId
        _showToast.value = 0
    }

    private val _paramsList = MutableStateFlow(mutableListOf<Param>())
    val paramsList: StateFlow<MutableList<Param>> = _paramsList

    fun onAddParamBtnClick() {
        showDialog()
    }

    fun onNewParamSubmit(key: String, value: String) {
        logD("Key: $key, Value: $value")
        if (key.isEmpty()) {
            showToast(R.string.not_valid_key)
        } else {
            _paramsList.value.add(Param(key, value))
        }
    }

    fun onSaveBtnClick() {
        logD()
    }

    fun onImportBtnClick() {
        logD()
    }

    fun onBaseUrlStateChange(url: String) {
        val isValid = Patterns.WEB_URL.matcher(url).matches()
        _addBtnEnabled.value = isValid
        _saveBtnEnabled.value = isValid
        _baseUrlError.value = if (isValid) 0 else R.string.not_valid_url
    }
}