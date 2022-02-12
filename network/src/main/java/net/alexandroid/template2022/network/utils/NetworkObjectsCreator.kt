package net.alexandroid.template2022.network.utils

import net.alexandroid.template2022.network.logs.OkHttpLogs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkObjectsCreator {
    fun createOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS)
        .addInterceptor(logger())
        .build()

    private fun logger() = HttpLoggingInterceptor(OkHttpLogs()).apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }
}