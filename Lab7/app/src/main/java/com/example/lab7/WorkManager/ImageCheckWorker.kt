package com.example.lab7.WorkManager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lab7.API.FlickrResponse
import com.example.lab7.API.RetrofitClient
import com.example.lab7.R
import retrofit2.Response

class ImageCheckWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("test", "do Work")
        val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useNotification = sharedPreferences.getBoolean("useNotification", false)

        try {
            Log.d("test", "do Work try1")
            val response: Response<FlickrResponse> = RetrofitClient.flickrApiService.getRecentPhotos()
            if (response.isSuccessful && response.body()?.photos?.photo?.isNotEmpty() == true) {
                if (useNotification) {
                    Log.d("test", "do Work try2")
                    sendNotification(applicationContext, "Новые фотографии", "Появились новые фотографии на сервере")
                }
                Log.d("test", "do Work try3")
                return Result.success()
            } else {
                Log.d("test", "do Work try4")
                return Result.failure()
            }
        } catch (e: Exception) {
            Log.d("test", "do Work try5")
            Log.d("test", "${e.message}")
            return Result.failure()
        }
    }

    private fun sendNotification(context: Context, title: String, message: String) {
        Log.d("test", "Send notify1")
        val notificationManager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        Log.d("test", "Send notify2")

        if (ActivityCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("test", "Notify error")
            Log.d("test", "${ActivityCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            )}")
            return
        }
        Log.d("test", "Notify sent")
        notificationManager.notify(1, notification)
    }
}