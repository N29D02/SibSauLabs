package com.example.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab7.API.RetrofitClient
import com.example.lab7.ui.theme.Lab7Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlickrPhotosScreen()
        }
    }
}

@Composable
fun FlickrPhotosScreen() {
    val context = LocalContext.current
    var photosText by remember { mutableStateOf("Loading...") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.flickrApiService.getRecentPhotos(apiKey = "dbe2234de5e71758f77eda26b415fe41")
                if (response.isSuccessful) {
                    val photos = response.body()?.photos?.photo
                    photosText = photos?.joinToString("\n") { it.title } ?: "No photos found"
                } else {
                    photosText = "Failed to load photos"
                }
            } catch (e: Exception) {
                photosText = "Error: ${e.message}"
            }
        }
    }

    Column {
        Text(text = photosText)
    }
}

