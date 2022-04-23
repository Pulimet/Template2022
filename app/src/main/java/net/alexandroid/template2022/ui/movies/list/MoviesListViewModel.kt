package net.alexandroid.template2022.ui.movies.list

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.work.WorkManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.movies.Movie
import net.alexandroid.template2022.repo.movie.MovieResult
import net.alexandroid.template2022.repo.movie.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.worker.MovieWorker
import kotlin.coroutines.CoroutineContext

class MoviesListViewModel(
    private val moviesRepo: MoviesRepo,
    private val ioCoroutineContext: CoroutineContext,
    private val workManager: WorkManager
) : BaseViewModel() {

    lateinit var navViewModel: NavViewModel
    var savedItemPosition = -1

    private val _uiState = MutableStateFlow<MovieResult>(MovieResult.Empty)
    val uiState: StateFlow<MovieResult> = _uiState

    private var fetchMoviesJob: Job? = null


    fun onFragmentViewCreated() {
        logD()
        observeMoviesFromDb()
        fetchMoviesFromNetwork()
    }

    private fun observeMoviesFromDb() {
        logD()
        viewModelScope.launch(ioCoroutineContext) {
            moviesRepo.getMoviesFromDb().collectLatest {
                _uiState.value = MovieResult.Success(it)
            }
        }
    }

    fun onUserRefreshedMain() {
        logD()
        savedItemPosition = 0
        fetchMoviesFromNetwork()
    }

    private fun fetchMoviesFromNetwork() {
        logD()
        _uiState.value = MovieResult.Loading
        fetchMoviesJob?.cancel()
        fetchMoviesJob = viewModelScope.launch(ioCoroutineContext) {
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

    fun onMenuScheduleClick() {
        MovieWorker.launch(workManager)
    }
}