package net.alexandroid.template2022.ui.api.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.repo.api.ApiRepo
import net.alexandroid.template2022.repo.api.ApiResult
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.utils.logs.logE
import kotlin.coroutines.CoroutineContext

class ApiListViewModel(
    private val apiRepo: ApiRepo,
    private val ioCoroutineContext: CoroutineContext,
) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel

    private val _showToast = MutableStateFlow<String?>(null)
    val showToast: StateFlow<String?> = _showToast

    fun onFabAddApiClick() {
        navViewModel.navigateTo(ApiListFragmentDirections.actionApiListFragmentToApiAddFragment())
    }

    fun apiList() = apiRepo.getAll()

    fun onBtnEditApi(api: Api) {
        navViewModel.navigateTo(ApiListFragmentDirections.actionApiListFragmentToApiAddFragment(api))
    }

    fun onBtnDeleteApi(api: Api) {
        viewModelScope.launch(ioCoroutineContext) {
            apiRepo.deleteApi(api)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    fun onApiClick(api: Api) {
        viewModelScope.launch(ioCoroutineContext) {
            when (val apiResult = apiRepo.callFor(api)) {
                is ApiResult.Success -> {
                    val msg = apiResult.responseBody.string()
                    logD(msg)
                    _showToast.value = "Success: ${msg.substring(0, msg.length.coerceAtMost(100))}"
                }
                is ApiResult.Error -> {
                    val msg = apiResult.exception.message ?: ""
                    logE("Failed: $msg")
                    _showToast.value = "Failed: ${msg.substring(0, msg.length.coerceAtMost(100))}"
                }
            }
        }
    }

    fun onBtnScheduleApi(api: Api) {
        navViewModel.navigateTo(
            ApiListFragmentDirections.actionApiListFragmentToScheduleApiFragment(api)
        )
    }
}