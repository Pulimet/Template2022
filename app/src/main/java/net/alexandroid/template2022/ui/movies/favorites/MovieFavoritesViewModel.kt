package net.alexandroid.template2022.ui.movies.favorites

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.alexandroid.template2022.db.model.movies.Movie
import net.alexandroid.template2022.db.utils.MovieModelConverter
import net.alexandroid.template2022.repo.movie.MoviesRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class MovieFavoritesViewModel(moviesRepo: MoviesRepo) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel
    var savedItemPosition = -1

    val favoritesList = moviesRepo.getFavoriteMovies().map {
        MovieModelConverter.convertMovieFavoriteToMovie(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun saveClickedItemPosition(position: Int) {
        savedItemPosition = position
    }

    fun onUserMovieClick(movie: Movie, extras: FragmentNavigator.Extras) {
        navViewModel.navigateTo(
            MovieFavoritesFragmentDirections.actionMovieFavoritesFragmentToMovieDetailsFragment(
                movie
            ),
            extras
        )
    }
}