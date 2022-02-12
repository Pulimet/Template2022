package net.alexandroid.template2022.network.logs

import okhttp3.logging.HttpLoggingInterceptor

@Suppress("ConstantConditionIf")
class OkHttpLogs : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        //TODO logI(message)
    }
}