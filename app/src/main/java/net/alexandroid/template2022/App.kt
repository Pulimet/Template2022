package net.alexandroid.template2022

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.alexandroid.template2022.di.Di
import net.alexandroid.template2022.utils.logs.logD
import kotlin.coroutines.CoroutineContext

@Suppress("unused")
class App : Application(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun onCreate() {
        super.onCreate()
        logD("=== Application Created ===")
        launch { Di.setup(applicationContext) }
    }
}