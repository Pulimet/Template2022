package net.alexandroid.template2022.utils.logs

import okhttp3.logging.HttpLoggingInterceptor

@Suppress("ConstantConditionIf")
class OkHttpLogs : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        logI(message)
    }
}