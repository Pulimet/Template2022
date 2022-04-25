package net.alexandroid.template2022.ui.api.add

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.db.model.api.Param
import net.alexandroid.template2022.repo.api.ApiRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD
import java.net.URI
import java.net.URISyntaxException
import kotlin.coroutines.CoroutineContext

class ApiAddViewModel(
    private val apiRepo: ApiRepo,
    private val ioCoroutineContext: CoroutineContext
) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    private val _showAddParamDialog = MutableStateFlow<Param?>(null)
    val showAddParamDialog: StateFlow<Param?> = _showAddParamDialog

    private fun showAddParamDialog() {
        _showAddParamDialog.value = Param("", "")
        _showAddParamDialog.value = null
    }

    private fun showEditParamDialog(param: Param) {
        _showAddParamDialog.value = param
        _showAddParamDialog.value = null
    }

    private val _showImportUrlDialog = MutableStateFlow(false)
    val showImportUrlDialog: StateFlow<Boolean> = _showImportUrlDialog

    private fun showImportUrlDialog() {
        _showImportUrlDialog.value = true
        _showImportUrlDialog.value = false
    }

    private val _addBtnEnabled = MutableStateFlow(false)
    val addBtnState: StateFlow<Boolean> = _addBtnEnabled

    private val _saveBtnEnabled = MutableStateFlow(false)
    val saveBtnState: StateFlow<Boolean> = _saveBtnEnabled

    private val _baseUrl = MutableStateFlow("")
    val baseUrl: StateFlow<String> = _baseUrl

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
        showAddParamDialog()
    }

    fun onNewParamSubmit(param: Param) {
        logD("Key: ${param.key}, Value: ${param.value}")
        if (param.key.isEmpty()) {
            showToast(R.string.not_valid_key)
        } else {
            // TODO If the key exist, edit instead adding new one
            _paramsList.value.add(param)
        }
    }

    fun onSaveBtnClick(baseUrl: String) {
        viewModelScope.launch(ioCoroutineContext) {
            apiRepo.addApi(Api(baseUrl, paramsList.value))
        }
        navViewModel.navigateUp()
    }

    fun onImportBtnClick() {
        showImportUrlDialog()
    }

    fun onSubmitImportingUrl(inputUrl: String) {
        logD("inputUrl: $inputUrl")
        if (inputUrl.isEmpty()) {
            showToast(R.string.not_valid_empty_url)
            return
        }
        val url = addSchemaIfMissing(inputUrl)
        try {
            val uri = URI(url)
            logD("scheme: ${uri.scheme}, domain: ${uri.host}, query: ${uri.query}")
            _baseUrl.value = "${uri.scheme}://${uri.host}"
            parseAndUpdateParams(uri)
        } catch (e: URISyntaxException) {
            showToast(R.string.not_valid_url)
        }
    }

    private fun parseAndUpdateParams(uri: URI) {
        _paramsList.value.clear()
        val params = uri.query.split("&")
        params.forEach {
            val keyValue = it.split("=")
            if (keyValue.size == 2) {
                _paramsList.value.add(Param(keyValue[0], keyValue[1]))
            }
        }
    }

    private fun addSchemaIfMissing(inputUrl: String): String {
        if (!inputUrl.startsWith("http://") && !inputUrl.startsWith("https://")) {
            return "http://$inputUrl"
        }
        return inputUrl
    }

    fun onBaseUrlChanged(url: String) {
        val isValid = Patterns.WEB_URL.matcher(url).matches()
        _addBtnEnabled.value = isValid
        _saveBtnEnabled.value = isValid
        _baseUrlError.value = if (isValid) 0 else R.string.not_valid_url
    }

    fun onBtnEditParamClick(param: Param) {
        showEditParamDialog(param)
    }

    fun onBtnDeleteParamClick(param: Param) {
        // TODO Support deleting param
    }
}