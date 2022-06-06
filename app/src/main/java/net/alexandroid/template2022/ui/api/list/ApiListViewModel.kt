package net.alexandroid.template2022.ui.api.list

import androidx.lifecycle.viewModelScope
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
            val apiResult = apiRepo.callFor(api)
            when (apiResult) {
                is ApiResult.Success -> {
                    logD(apiResult.responseBody.string())
                }
                is ApiResult.Error -> {
                    logE("Failed", t = apiResult.exception)
                }
            }

        }
    }

    fun onBtnScheduleApi(api: Api) {
        // TODO support scheduling Api call with result as notification
        // TODO support scheduling notification with action to invoke api
    }
}