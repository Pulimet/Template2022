package net.alexandroid.template2022.db.model.api

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "api")
data class Api(
    @PrimaryKey
    val id: Int,
    val baseUrl: String,
    val params: List<Param>
) : Parcelable {
    fun getUrl(): String {
        return "" // TODO
    }
}