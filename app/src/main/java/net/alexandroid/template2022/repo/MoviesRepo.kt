package net.alexandroid.template2022.repo

import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.utils.logI

class MoviesRepo(private val tmdbApiService: TmdbApiService) {
    init {
        logI("init")
    }

    suspend fun getMovies() = try {
        MovieResult.Success(tmdbApiService.getMovies())
    } catch (e: Exception) {
        MovieResult.Error(e)
    }
}