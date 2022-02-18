package net.alexandroid.template2022.ui.movies.list

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.repo.MovieResult
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logD

class MoviesListViewModel(
    private val moviesRepo: MoviesRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel
    var savedItemPosition = -1

    private val _uiState = MutableStateFlow<MovieResult>(MovieResult.Empty)
    val uiState: StateFlow<MovieResult> = _uiState

    private var fetchMoviesJob: Job? = null


    fun onFragmentViewCreated() {
        logD()
        observeMoviesFromDb()
    }

    private fun observeMoviesFromDb() {
        logD()
        viewModelScope.launch(ioDispatcher) {
            moviesRepo.getMoviesFromDb().collect {
                _uiState.value = MovieResult.Success(it)
            }
        }
    }

    fun onUserRefreshedMain() {
        logD()
        fetchMoviesFromNetwork()
    }

    private fun fetchMoviesFromNetwork() {
        logD()
        _uiState.value = MovieResult.Loading
        fetchMoviesJob?.cancel()
        fetchMoviesJob = viewModelScope.launch(ioDispatcher) {
            moviesRepo.getMoviesFromNetwork()
        }
    }

    fun onUserMovieClick(movie: Movie, extras: FragmentNavigator.Extras, position: Int) {
        savedItemPosition = position
        navViewModel.navigateTo(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movie),
            extras
        )
    }

    fun onFavoritesClick(position: Int) {
        savedItemPosition = position
        navViewModel.navigateTo(MoviesListFragmentDirections.actionMoviesListFragmentToMovieFavoritesFragment())
    }

    fun onSettingsClick(position: Int) {
        savedItemPosition = position
        navViewModel.navigateTo(MoviesListFragmentDirections.actionMoviesListFragmentToMovieSettingsFragment())
    }
}