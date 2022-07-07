package net.alexandroid.template2022.worker

import android.content.Context
import android.os.Build
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import net.alexandroid.template2022.R
import net.alexandroid.template2022.repo.movie.MoviesRepo
import net.alexandroid.template2022.utils.Notifications
import net.alexandroid.template2022.utils.logs.logD
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class MovieWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params), KoinComponent {

    companion object {
        private const val WORK_NAME_FETCH_MOVIES = "WORK_NAME_FETCH_MOVIES"

        fun launch(workManager: WorkManager) {
            logD("WorkManager.enqueueUniquePeriodicWork(...)")
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME_FETCH_MOVIES,
                ExistingPeriodicWorkPolicy.KEEP,
                getPeriodicWorkRequest()
            )
        }

        private fun getPeriodicWorkRequest() = PeriodicWorkRequestBuilder<MovieWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(30, TimeUnit.SECONDS)
            .setConstraints(getConstraints())
            .build()

        private fun getConstraints() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            /*.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }*/
            //.setRequiresBatteryNotLow(true)
            //.setRequiresCharging(true)
            .build()
    }

    private val moviesRepo: MoviesRepo by inject()

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val message = applicationContext.getString(R.string.notify_msg_loading_movies)
        return ForegroundInfo(
            Notifications.NOTIFICATION_ID,
            Notifications.createMoviesNotification(message, applicationContext)
        )
    }

    override suspend fun doWork(): Result = coroutineScope {
        logD("=== Movie Worker Lunched ===")
        if (moviesRepo.getMoviesFromNetwork()) {
            delay(30000)
            logD("Movies list updated in background")
            Result.success()
        } else {
            logD("Movies list failed to update in background")
            Result.failure()
        }
    }
}