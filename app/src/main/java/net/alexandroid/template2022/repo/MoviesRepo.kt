package net.alexandroid.template2022.repo

import kotlinx.coroutines.flow.Flow
import net.alexandroid.template2022.db.dao.MovieFavoriteDao
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.db.model.MovieFavorite
import net.alexandroid.template2022.db.utils.MovieModelConverter
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.utils.logI

class MoviesRepo(
    private val tmdbApiService: TmdbApiService,
    private val movieFavoriteDao: MovieFavoriteDao
) {
    init {
        logI("init")
    }

    suspend fun getMovies() = try {
        val movies = tmdbApiService.getMovies()
        MovieResult.Success(MovieModelConverter.convertTmdbResultsToListOfMovies(movies))
    } catch (e: Exception) {
        MovieResult.Error(e)
    }

    // Favorites
    fun getMovieFromFavorites(movieId: Int): Flow<MovieFavorite> =
        movieFavoriteDao.getMovie(movieId)

    suspend fun addOrRemoveMovieFromFavorites(movie: Movie, movieInFavorites: Boolean) {
        val movieFavorite: MovieFavorite = MovieModelConverter.convertMovieToMovieFavorite(movie)
        movieFavoriteDao.run {
            if (movieInFavorites) delete(movieFavorite) else insert(movieFavorite)
        }
    }
}