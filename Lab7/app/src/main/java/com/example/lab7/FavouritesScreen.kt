package com.example.lab7

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lab7.API.Photo
import com.example.lab7.database.PhotoDao
import com.example.lab7.viewModels.GalleryActivityVM
import com.example.lab7.viewModels.SearchActivityVM
import com.example.lab7.views.PhotoItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FavoritesScreen(photoDao: PhotoDao, onPhotoClick: (com.example.lab7.API.Photo) -> Unit) {
    val photos by photoDao.getAllPhotosLiveData().observeAsState(initial = emptyList())

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(photos) { dbPhoto ->
            val apiPhoto = dbPhotoToApiPhoto(dbPhoto)
            PhotoItem(apiPhoto, onPhotoClick)
        }
    }
}