package net.alexandroid.template2022.network.services

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiCallerService {
    @GET
    suspend fun call(@Url url: String): ResponseBody
}