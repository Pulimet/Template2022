package net.alexandroid.template2022.network.models

import com.google.gson.annotations.SerializedName

object TmdbNet {
    data class Discover(
        val page: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int,
        val results: MutableList<Movie>
    )

    data class Movie(
        val id: Int,
        val title: String,
        @SerializedName("poster_path") val posterImg: String?,
        @SerializedName("backdrop_path") val backImg: String?,
        val overview: String,
        @SerializedName("release_date") val date: String,
        @SerializedName("vote_average") val vote: Double,
        @SerializedName("vote_count") val voteCount: Int
    )
}