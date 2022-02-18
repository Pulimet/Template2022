package net.alexandroid.template2022.repo

import kotlinx.coroutines.flow.Flow
import net.alexandroid.template2022.db.dao.MovieDao
import net.alexandroid.template2022.db.dao.MovieFavoriteDao
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.db.model.MovieFavorite
import net.alexandroid.template2022.db.utils.MovieModelConverter
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.utils.logI

class MoviesRepo(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val movieFavoriteDao: MovieFavoriteDao
) {
    private var isFirstTime = true

    init {
        logI("init")
    }

    fun getMoviesFromDb() = movieDao.getMovies()

    suspend fun getMoviesFromNetwork() {
        val movies = tmdbApiService.getMovies()
        val convertedMovies = MovieModelConverter.convertTmdbResultsToListOfMovies(movies)
        saveFreshMoviesToDb(convertedMovies)
    }

    private suspend fun saveFreshMoviesToDb(convertedMovies: List<Movie>) {
        movieDao.deleteAll()
        movieDao.insertAll(convertedMovies)
    }

    // Favorites
    fun getMovieFromFavorites(movieId: Int): Flow<MovieFavorite> =
        movieFavoriteDao.getMovie(movieId)

    suspend fun addOrRemoveMovieFromFavorites(movie: Movie, movieInFavorites: Boolean) {
        val movieFavorite: MovieFavorite =
            MovieModelConverter.convertMovieToMovieFavorite(movie)
        movieFavoriteDao.run {
            if (movieInFavorites) delete(movieFavorite) else insert(movieFavorite)
        }
    }

    fun getFavoriteMovies() = movieFavoriteDao.getMovies()
}