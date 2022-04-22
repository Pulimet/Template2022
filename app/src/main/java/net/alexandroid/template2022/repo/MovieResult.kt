package net.alexandroid.template2022.repo

import net.alexandroid.template2022.db.model.movies.Movie

sealed class MovieResult {
    object Empty : MovieResult()
    object Loading : MovieResult()
    data class Success(val moviesList: List<Movie>) : MovieResult()
    data class Error(val exception: Exception) : MovieResult()
}