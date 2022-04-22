package net.alexandroid.template2022.ui.movies.details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.alexandroid.template2022.db.model.movies.Movie
import net.alexandroid.template2022.db.model.movies.MovieFavorite
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class MovieDetailsViewModel(private val moviesRepo: MoviesRepo) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel
    private var isMovieInFavorites = false

    fun getMovieFromFavorites(movieId: Int): StateFlow<Boolean> =
        moviesRepo.getMovieFromFavorites(movieId)
            .map { movieFavorite: MovieFavorite? ->
                isMovieInFavorites = movieFavorite != null
                isMovieInFavorites
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun onFavoriteImageClick(movieId: Movie) {
        viewModelScope.launch {
            moviesRepo.addOrRemoveMovieFromFavorites(movieId, isMovieInFavorites)
        }
    }
}