package net.alexandroid.template2022.ui.api.list

import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class ApiListViewModel : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    fun onFabAddApiClick() {
        navViewModel.navigateTo(ApiListFragmentDirections.actionApiListFragmentToApiAddFragment())
    }
}