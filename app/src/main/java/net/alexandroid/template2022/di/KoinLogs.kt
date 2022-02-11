package net.alexandroid.template2022.di

import net.alexandroid.template2022.utils.logD
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class KoinLogs : Logger(level = Level.DEBUG) {
    override fun log(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> logD(msg)
            Level.INFO -> logD(msg)
            Level.ERROR -> logD(msg)
            else -> {}
        }
    }
}