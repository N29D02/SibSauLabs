package com.example.lab7.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.lab7.API.Photo
import com.example.lab7.viewModels.GalleryActivityVM
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun GalleryScreen(viewModel: GalleryActivityVM, onPhotoClick: (Photo) -> Unit) {
    val photos by viewModel.photos.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            viewModel.loadPhotos()
            isRefreshing = false
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(photos) { photo ->
                PhotoItem(photo, onPhotoClick)
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onClick: (Photo) -> Unit) {
    val imageUrl = "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(photo) }
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = photo.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(text = photo.title, modifier = Modifier.padding(top = 4.dp))
    }
}