package net.alexandroid.template2022.repo

import net.alexandroid.template2022.db.utils.MovieModelConverter
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.utils.logI

class MoviesRepo(private val tmdbApiService: TmdbApiService) {
    init {
        logI("init")
    }

    suspend fun getMovies() = try {
        val movies = tmdbApiService.getMovies()
        MovieResult.Success(MovieModelConverter.convertTmdbResultsToListOfMovies(movies))
    } catch (e: Exception) {
        MovieResult.Error(e)
    }
}