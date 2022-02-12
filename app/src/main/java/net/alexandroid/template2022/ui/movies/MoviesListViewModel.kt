package net.alexandroid.template2022.ui.movies

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.repo.MovieResult
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel

class MoviesListViewModel(
    private val moviesRepo: MoviesRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {
    var savedItemPosition = 0

    private val _uiState = MutableStateFlow<MovieResult>(MovieResult.Empty)
    val uiState: StateFlow<MovieResult> = _uiState

    fun onUserRefreshedMain() {
        TODO("Not yet implemented")
    }

    fun saveClickedItemPosition(position: Int) {
        TODO("Not yet implemented")
    }

    fun onUserMovieClick(movie: Movie, extras: FragmentNavigator.Extras) {
        TODO("Not yet implemented")
    }

    fun fetchMovies() {
        _uiState.value = MovieResult.Loading
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = moviesRepo.getMovies()
        }
    }
}