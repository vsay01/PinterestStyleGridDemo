package com.vsay.pintereststylegriddemo.core.platform_utils.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.vsay.pintereststylegriddemo.core.platform_utils.R

object NotificationHelper {
    private const val CHANNEL_ID =
        "demo_channel" // Consider making this more specific if you have multiple channels
    private const val TAG = "NotificationHelper"

    /**
     * Shows a notification that deep links to an image detail.
     *
     * IMPORTANT: The caller is responsible for ensuring that the POST_NOTIFICATIONS
     * permission has been granted on Android 13 (API 33) and above before calling this method.
     *
     * @param context Context.
     * @param imageId The ID of the image to link to.
     * @param contentTitle The title for the notification.
     * @param contentText The main text for the notification.
     */
    @SuppressLint("MissingPermission") // Suppressed because permission check is handled by the caller
    fun showDeepLinkNotification(
        context: Context,
        imageId: String,
        contentTitle: String,
        contentText: String,
        smallIconResId: Int,
    ) {
        val appContext = context.applicationContext

        // Intent for the deep link
        val intent = Intent(Intent.ACTION_VIEW, "myapp://detail/$imageId".toUri()).apply {
            // Add package to make it explicit if multiple apps could handle this,
            // otherwise, rely on AndroidManifest's intent-filter.
            // setPackage(appContext.packageName)
        }

        val pendingIntent = PendingIntent.getActivity(
            appContext,
            imageId.hashCode(), // Unique request code
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
            // IMPORTANT: Replace with your actual small notification icon
            .setSmallIcon(smallIconResId)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        // .setGroup("recommended_images_group") // Optional

        try {
            with(NotificationManagerCompat.from(appContext)) {
                notify(
                    imageId.hashCode(),
                    builder.build()
                )
            }
        } catch (e: SecurityException) {
            Log.e(
                TAG,
                "Failed to send notification. Missing POST_NOTIFICATIONS permission or other issue.",
                e
            )
        }
    }

    /**
     * Creates the notification channel for the application.
     * Should be called once, e.g., in Application.onCreate() or MainActivity.onCreate().
     *
     * @param context Context.
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appContext = context.applicationContext
            val name =
                appContext.getString(R.string.notification_channel_name)
            val descriptionText =
                appContext.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}
