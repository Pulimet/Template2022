package net.alexandroid.template2022.ui.api.list

import net.alexandroid.template2022.repo.api.ApiRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class ApiListViewModel(private val apiRepo: ApiRepo) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    fun onFabAddApiClick() {
        navViewModel.navigateTo(ApiListFragmentDirections.actionApiListFragmentToApiAddFragment())
    }

    fun apiList() = apiRepo.getAll()
}