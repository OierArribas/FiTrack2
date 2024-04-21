package com.example.fittrack

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import android.content.Context
import android.content.SharedPreferences


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


        Firebase.messaging.token.addOnCompleteListener{
            if (!it.isSuccessful){
                println("el token no fue generado")
                return@addOnCompleteListener
            } else {
                val token = it.result
                Log.i("FirebaseToken", token)


            }
        }
        createNotificationChannel()


    }

    companion object{
        val NOTIFICATION_CHANNEL_ID = "notification_fcm"
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Notificacion de FCM",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}


