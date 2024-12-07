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
import com.example.lab7.API.Photo
import com.example.lab7.API.RetrofitClient
import com.example.lab7.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import kotlin.random.Random

class ImageCheckWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val sharedPreferences = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val useNotification = sharedPreferences.getBoolean("useNotification", false)

        try {
            val response: Response<FlickrResponse> = RetrofitClient.flickrApiService.getRecentPhotos()
            if (response.isSuccessful && response.body()?.photos?.photo?.isNotEmpty() == true) {
                if (useNotification) {
                    val photos = response.body()?.photos?.photo ?: emptyList()
                    val lastPhotos = getLastPhotos(applicationContext)

                    if (photos != lastPhotos) {
                        saveLastPhotos(applicationContext, photos)
                        if (useNotification) {
                            sendNotification(applicationContext, "Новые фотографии", "Появились новые фотографии на сервере")
                        }
                    }
                }
                return Result.success()
            } else {
                return Result.failure()
            }
        } catch (e: Exception) {
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

        if (ActivityCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun getLastPhotos(context: Context): List<Photo> {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("lastPhotos", null)
        return json?.let { Gson().fromJson(it, object : TypeToken<List<Photo>>() {}.type) } ?: emptyList()
    }

    private fun saveLastPhotos(context: Context, photos: List<Photo>) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val json = Gson().toJson(photos)
        sharedPreferences.edit().putString("lastPhotos", json).apply()
    }
}