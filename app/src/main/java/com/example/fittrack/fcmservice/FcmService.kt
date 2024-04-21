package com.example.fittrack.fcmservice

import android.annotation.SuppressLint
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.fittrack.MyApplication
import com.example.fittrack.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, MyApplication.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.firebase_notificaation)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1 , notification)
    }
}