package net.alexandroid.template2022.ui.movies.list

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
import net.alexandroid.template2022.ui.navigation.NavViewModel

class MoviesListViewModel(
    private val moviesRepo: MoviesRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel
    var savedItemPosition = -1

    private val _uiState = MutableStateFlow<MovieResult>(MovieResult.Empty)
    val uiState: StateFlow<MovieResult> = _uiState

    fun onUserRefreshedMain() {
        fetchMovies()
    }

    fun saveClickedItemPosition(position: Int) {
        savedItemPosition = position
    }

    fun onUserMovieClick(movie: Movie, extras: FragmentNavigator.Extras) {
        navViewModel.navigateTo(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movie),
            extras
        )
    }

    fun fetchMovies() {
        _uiState.value = MovieResult.Loading
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = moviesRepo.getMovies()
        }
    }
}