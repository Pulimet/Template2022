package net.alexandroid.template2022

import android.app.Application
import net.alexandroid.template2022.di.Di
import net.alexandroid.template2022.utils.logD

@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        logD("=== Application Created ===")
        Di.setup(applicationContext)
    }
}