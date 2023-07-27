package com.kisusyenni.disasterapp.utils

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kisusyenni.disasterapp.R
import com.kisusyenni.disasterapp.view.MainActivity

class NotificationHelper(val context: Context) {

    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            AlertDialog.Builder(context)
                .setTitle("Required Notification Permission")
                .setMessage("This app needs the Notification permission to alert high water level")
                .setPositiveButton(
                    "Allow"
                ) { _, _ ->
                    //Prompt the user once explanation has been shown
                    requestNotificationPermission()
                }
                .create()
                .show()
            return
        }

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            MainActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            NOTIFICATION_ID
        )
    }

    companion object {
        const val CHANNEL_ID = "alert_water_level"
        const val NOTIFICATION_ID = 1
    }
}