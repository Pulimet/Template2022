package net.alexandroid.template2022.ui.api.add

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _showAddParamDialog = MutableSharedFlow<Param>()
    val showAddParamDialog = _showAddParamDialog.asSharedFlow()

    private fun showAddParamDialog() {
        viewModelScope.launch { _showAddParamDialog.emit(Param("", "")) }
    }

    private fun showEditParamDialog(param: Param) {
        viewModelScope.launch { _showAddParamDialog.emit(param) }
    }

    private val _showImportUrlDialog = MutableSharedFlow<Unit>()
    val showImportUrlDialog = _showImportUrlDialog.asSharedFlow()

    private fun showImportUrlDialog() {
        viewModelScope.launch { _showImportUrlDialog.emit(Unit) }
    }

    private val _addBtnEnabled = MutableStateFlow(false)
    val addBtnState = _addBtnEnabled.asStateFlow()

    private val _saveBtnEnabled = MutableStateFlow(false)
    val saveBtnState = _saveBtnEnabled.asStateFlow()

    private val _baseUrl = MutableStateFlow("")
    val baseUrl = _baseUrl.asStateFlow()

    private val _baseUrlError = MutableStateFlow(0)
    val baseUrlError = _baseUrlError.asStateFlow()

    private val _showToast = MutableSharedFlow<Int>()
    val showToast = _showToast.asSharedFlow()

    private fun showToast(resId: Int) {
        viewModelScope.launch { _showToast.emit(resId) }
    }

    private val _paramsList = MutableStateFlow(listOf<Param>())
    val paramsList = _paramsList.asStateFlow()

    fun onAddParamBtnClick() {
        showAddParamDialog()
    }

    fun loadArgumentApi(api: Api) {
        _baseUrl.value = api.baseUrl
        _paramsList.value = api.params
    }

    fun onSubmitParam(param: Param, isEditModeParam: Param?) {
        logD("Key: ${param.key}, Value: ${param.value} (isEditModeParam: $isEditModeParam)")
        if (param.key.isEmpty()) {
            showToast(R.string.not_valid_key)
            return
        }
        val newList = _paramsList.value.toMutableList()
        newList.apply {
            val keyToRemove = isEditModeParam?.key ?: param.key
            find { it.key == keyToRemove }?.let { remove(it) } // Remove if found param with the same key
            add(param)
        }
        _paramsList.value = newList.toList()
    }

    fun onSaveBtnClick(baseUrl: String, apiArgument: Api?) {
        viewModelScope.launch(ioCoroutineContext) {
            val url = addSchemaIfMissing(baseUrl)
            val api = Api(url, paramsList.value)
            if (apiArgument == null) {
                apiRepo.addApi(api)
            } else {
                apiRepo.editApi(api, apiArgument)
            }
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
            logD("scheme: ${uri.scheme}, domain: ${uri.host}, path: ${uri.path}, query: ${uri.query}")
            _baseUrl.value = "${uri.scheme}://${uri.host}${uri.path}"
            parseAndUpdateParams(uri)
        } catch (e: URISyntaxException) {
            showToast(R.string.not_valid_url)
        }
    }

    private fun parseAndUpdateParams(uri: URI) {
        logD("Query: ${uri.query}")
        _paramsList.value = emptyList()
        if (uri.query == null) return

        val newList = mutableListOf<Param>()
        val params = uri.query.split("&")
        params.forEach {
            val keyValue = it.split("=")
            if (keyValue.size == 2) {
                newList.add(Param(keyValue[0], keyValue[1]))
            }
        }
        _paramsList.value = newList.toList()
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
        val newList = _paramsList.value.toMutableList()
        newList.apply {
            find { it.key == param.key }?.let { remove(it) } // Remove if found param with the same key
        }
        _paramsList.value = newList.toList()
    }
}