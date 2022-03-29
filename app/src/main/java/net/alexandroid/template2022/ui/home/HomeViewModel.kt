package net.alexandroid.template2022.ui.home

import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class HomeViewModel : BaseViewModel() {

    lateinit var navViewModel: NavViewModel

    fun onBtnOpenFragmentClick() {
        navViewModel.navigateTo(HomeFragmentDirections.actionHomeFragmentToExampleFragment())
    }

    fun onBtnOpenActivityClick() {
        //navViewModel.startActivity(ExampleActivity::class.java)
        navViewModel.navigateTo(HomeFragmentDirections.actionHomeFragmentToExampleActivity())
    }

    fun onBntMoviesClick() {
        navViewModel.navigateTo(HomeFragmentDirections.actionHomeFragmentToMoviesListFragment())
    }

    fun onBtnApiClick() {
        navViewModel.navigateTo(HomeFragmentDirections.actionHomeFragmentToApiFragment())
    }
}