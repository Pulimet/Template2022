package net.alexandroid.template2022.db.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Param(
    var key: String,
    var value: String
) : Parcelable