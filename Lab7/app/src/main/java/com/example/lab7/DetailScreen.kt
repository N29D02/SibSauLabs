package com.example.lab7

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.lab7.API.Photo
import com.example.lab7.database.PhotoDao

@Composable
fun PhotoDetailScreen(photo: Photo, photoDao: PhotoDao, onAddToFavorites: (Photo) -> Unit, onRemoveFromFavorites: (Photo) -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isFavorite = photoDao.isPhotoInFavorites(photo.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"),
            contentDescription = photo.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(text = photo.title, modifier = Modifier.padding(top = 8.dp))
        Button(
            onClick = {
                if (isFavorite) {
                    onRemoveFromFavorites(photo)
                } else {
                    onAddToFavorites(photo)
                }
                isFavorite = !isFavorite
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
        }
    }
}