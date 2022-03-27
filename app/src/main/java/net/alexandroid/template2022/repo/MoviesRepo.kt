package net.alexandroid.template2022.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.alexandroid.template2022.db.dao.MovieDao
import net.alexandroid.template2022.db.dao.MovieFavoriteDao
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.db.model.MovieFavorite
import net.alexandroid.template2022.db.utils.MovieModelConverter
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.utils.ImageLoading
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.utils.logs.logE
import net.alexandroid.template2022.utils.logs.logI
import java.net.UnknownHostException

class MoviesRepo(
    private val tmdbApiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val movieFavoriteDao: MovieFavoriteDao,
    private val dataStoreVotes: DataStore<Preferences>,
    private val dataStoreRating: DataStore<Preferences>,
    private val imageLoading: ImageLoading
) {
    init {
        logI("init")
    }

    // Settings
    private fun getTempMinValue() = dataStoreVotes.data.map { preferences ->
        preferences[MovieSettingsRepo.KEY_MIN_VOTES] ?: 2
    }

    private fun getTempMinRating() = dataStoreRating.data.map { preferences ->
        preferences[MovieSettingsRepo.KEY_MIN_RATING] ?: 2
    }

    // Movies
    fun getMoviesFromDb() = movieDao.getMovies()

    suspend fun getMoviesFromNetwork(): Boolean {
        val minNumOfVotes = getTempMinValue().first()
        val minRating = getTempMinRating().first()

        return try {
            val movies = tmdbApiService.getMovies(
                minNumOfVotes = minNumOfVotes,
                minRating = minRating
            )
            val convertedMovies = MovieModelConverter.convertTmdbResultsToListOfMovies(movies)
            saveFreshMoviesToDb(convertedMovies)
            preloadMoviesImages(convertedMovies)
            true
        } catch (e: UnknownHostException) {
            logE("Failed to fetch movies from network. (${e.message})")
            false
        }
    }

    private suspend fun saveFreshMoviesToDb(movies: List<Movie>) {
        logD()
        movieDao.deleteAll()
        movieDao.insertAll(movies)
    }

    private fun preloadMoviesImages(movies: List<Movie>) {
        logD()
        imageLoading.preloadImages(movies)
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