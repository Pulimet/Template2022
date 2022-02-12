package net.alexandroid.template2022.ui.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.example.ExampleActivity
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logI

class HomeViewModel(private val tmdbApiService: TmdbApiService) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel

    fun onBtnOpenFragmentClick() {
        navViewModel.navigateTo(HomeFragmentDirections.actionMainFragmentToExampleFragment())
    }

    fun onBtnOpenActivityClick() {
        navViewModel.startActivity(ExampleActivity::class.java)
    }

    fun onBtnNetworkTestClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = tmdbApiService.getMovies()
            logI(result.results[0].title)
        }
    }
}