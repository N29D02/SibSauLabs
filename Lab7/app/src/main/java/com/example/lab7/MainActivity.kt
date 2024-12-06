package com.example.lab7

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.lab7.API.Photo
import com.example.lab7.API.RetrofitClient
import com.example.lab7.WorkManager.ImageCheckWorker
import com.example.lab7.ui.theme.Lab7Theme
import com.example.lab7.navigations.NavGraph
import com.example.lab7.viewModels.GalleryActivityVM
import com.example.lab7.viewModels.SettingsActivityVM
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MainActivity : ComponentActivity() {
    private lateinit var galleryActivityVM: GalleryActivityVM
    private lateinit var settingActivityVM: SettingsActivityVM



    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->

    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(this)

        galleryActivityVM = ViewModelProvider(this)[GalleryActivityVM::class.java]
        settingActivityVM = ViewModelProvider(this)[SettingsActivityVM::class.java]

        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            Lab7Theme {
                Scaffold(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), topBar = {
                    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                        title = { Text("${currentRoute}") }, actions = {
                            if (currentRoute == "Galery"){
                                TextButton(modifier = Modifier.background(color = Color.Transparent), onClick =
                                {
                                    navController.navigate("Galery")
                                }) {
                                    Icon(imageVector = Icons.Default.List, contentDescription = "Stored")
                                }
                                TextButton(modifier = Modifier.background(color = Color.Transparent), onClick =
                                {
                                    navController.navigate("Galery")
                                }) {
                                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                                }
                                TextButton(modifier = Modifier.background(color = Color.Transparent), onClick =
                                {
                                    navController.navigate("Settings")
                                }) {
                                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                                }
                            }
                        })
                }) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        galleryActivityVM = galleryActivityVM,
                        settingsActivityVM = settingActivityVM
                    )
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        scheduleImageCheck(this)
    }

    private fun scheduleImageCheck(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ImageCheckWorker>(5, TimeUnit.SECONDS)
            .build()
        Log.d("test", "Schedule start")
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "ImageCheckWork",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
}

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Channel Name"
        val descriptionText = "Channel Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
