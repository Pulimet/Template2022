package net.alexandroid.template2022.db.model.movies

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import net.alexandroid.template2022.db.utils.MovieModelConverter

@Parcelize
@Entity(tableName = "movies_favorite")
data class MovieFavorite(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String,
    val date: String,
    val vote: Double,
    val voteCount: Int
) : Parcelable