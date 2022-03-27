package net.alexandroid.template2022.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import net.alexandroid.template2022.R

object Notifications {
    @JvmField
    val MOVIES_CHANNEL_NAME: CharSequence = "Movies Notifications"

    @JvmField
    val MOVIES_NOTIFICATION_TITLE: CharSequence = "Fetching Movies"

    private const val MOVIES_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts"
    private const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1


    fun createMoviesNotification(message: String, context: Context): Notification {
        makeChannelIfNecessary(context)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(MOVIES_NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        //NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        return builder.build()
    }

    private fun makeChannelIfNecessary(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = MOVIES_CHANNEL_NAME
            val description = MOVIES_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }
}