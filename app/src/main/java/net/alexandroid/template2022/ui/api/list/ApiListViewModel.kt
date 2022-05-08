package net.alexandroid.template2022.ui.api.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.repo.api.ApiRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
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
        // TODO support editing Api
        // navViewModel.navigateTo(ApiListFragmentDirections.actionApiListFragmentToApiAddFragment())
    }

    fun onBtnDeleteApi(api: Api) {
        viewModelScope.launch(ioCoroutineContext) {
            apiRepo.deleteApi(api)
        }
    }
}