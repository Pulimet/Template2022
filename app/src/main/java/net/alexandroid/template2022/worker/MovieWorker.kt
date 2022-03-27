package net.alexandroid.template2022.worker

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.coroutineScope
import net.alexandroid.template2022.R
import net.alexandroid.template2022.repo.MoviesRepo
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
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME_FETCH_MOVIES,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<MovieWorker>(15, TimeUnit.MINUTES).build()
            )
        }
    }

    private val moviesRepo: MoviesRepo by inject()

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val message = applicationContext.getString(R.string.notif_msg_loading_movies)
        return ForegroundInfo(
            Notifications.NOTIFICATION_ID,
            Notifications.createMoviesNotification(message, applicationContext)
        )
    }

    override suspend fun doWork(): Result = coroutineScope {
        logD("=== Movie Worker Lunched ===")
        if (moviesRepo.getMoviesFromNetwork()) {
            logD("Movies list updated in background")
            Result.success()
        } else {
            logD("Movies list failed to update in background")
            Result.failure()
        }
    }
}