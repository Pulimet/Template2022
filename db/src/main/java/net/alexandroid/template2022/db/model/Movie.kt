package net.alexandroid.template2022.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import net.alexandroid.template2022.db.utils.MovieModelConverter

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val overview: String,
    val date: String,
    val vote: Double,
    val voteCount: Int
) : Parcelable {
    fun getTitleWithYear() = title + " (" + MovieModelConverter.getYear(date) + ")"
}