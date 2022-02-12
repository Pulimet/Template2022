package net.alexandroid.template2022.repo

import net.alexandroid.template2022.network.models.TmdbNet

sealed class MovieResult {
    data class Success(val result: TmdbNet.Discover) : MovieResult()
    data class Error(val exception: Exception) : MovieResult()
}