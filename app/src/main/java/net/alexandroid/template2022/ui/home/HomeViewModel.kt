package net.alexandroid.template2022.ui.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.alexandroid.template2022.repo.MovieResult
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.example.ExampleActivity
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logE
import net.alexandroid.template2022.utils.logI

class HomeViewModel(
    private val moviesRepo: MoviesRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel

    fun onBtnOpenFragmentClick() {
        navViewModel.navigateTo(HomeFragmentDirections.actionMainFragmentToExampleFragment())
    }

    fun onBtnOpenActivityClick() {
        navViewModel.startActivity(ExampleActivity::class.java)
    }

    fun onBtnNetworkTestClick() {
        viewModelScope.launch(ioDispatcher) {
            when (val movieResult = moviesRepo.getMovies()) {
                is MovieResult.Success -> logI(movieResult.result.results[0].title)
                is MovieResult.Error -> logE("Failed to get movies, ${movieResult.exception.message}")
            }
        }
    }
}