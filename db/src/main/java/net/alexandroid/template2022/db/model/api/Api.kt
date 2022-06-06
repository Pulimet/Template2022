package net.alexandroid.template2022.db.model.api

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "api")
data class Api(
    @PrimaryKey
    val baseUrl: String,
    val params: List<Param>
) : Parcelable {
    fun getUrl(): String {
        val uriBuilder = Uri.parse(baseUrl).buildUpon()
        params.forEach {
            uriBuilder.appendQueryParameter(it.key, it.value)
        }
        return uriBuilder.build().toString()
    }
}