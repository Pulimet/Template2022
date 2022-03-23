package net.alexandroid.template2022.utils.logs

import android.util.Log
import coil.util.Logger

class CoilLogs : Logger {
    override var level = Log.DEBUG

    override fun log(tag: String, priority: Int, message: String?, throwable: Throwable?) {
        if (throwable != null) {
            logE(message ?: "Empty message", t = throwable)
        } else {
            logD(message ?: "Empty message")
        }
    }
}