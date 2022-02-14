package net.alexandroid.template2022.db.utils

import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.db.model.MovieFavorite
import net.alexandroid.template2022.network.models.TmdbNet
import java.text.SimpleDateFormat
import java.util.*

object MovieModelConverter {
    private const val TMDB_IMG_URL = "https://image.tmdb.org/t/p/w500"

    fun convertTmdbResultsToListOfMovies(movies: TmdbNet.Discover): List<Movie> {
        return movies.results.map {
            Movie(
                id = it.id,
                title = it.title,
                posterUrl = getImageUrl(it),
                overview = it.overview,
                date = it.date,
                vote = it.vote,
                voteCount = it.voteCount
            )
        }
    }

    private fun getImageUrl(movie: TmdbNet.Movie) =
        when {
            movie.posterImg != null -> TMDB_IMG_URL + movie.posterImg
            movie.backImg != null -> TMDB_IMG_URL + movie.backImg
            else -> null
        }

    fun getYear(date: String): String {
        val parsedDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
        val formatOut = SimpleDateFormat("yyyy", Locale.US)
        return if (parsedDate != null) {
            formatOut.format(parsedDate)
        } else {
            ""
        }
    }

    fun convertMovieToMovieFavorite(movie: Movie) = MovieFavorite(
        id = movie.id,
        title = movie.title,
        posterUrl = movie.posterUrl,
        overview = movie.overview,
        date = movie.date,
        vote = movie.vote,
        voteCount = movie.voteCount
    )
}