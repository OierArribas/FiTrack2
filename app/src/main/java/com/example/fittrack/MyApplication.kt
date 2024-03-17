package com.example.fittrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi


@HiltAndroidApp
class MyApplication: Application(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val notificationChannel=NotificationChannel(
            "water_notification",
            "Water",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}


