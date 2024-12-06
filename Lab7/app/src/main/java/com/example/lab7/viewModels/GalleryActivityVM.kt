package com.example.lab7.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.API.FlickrResponse
import com.example.lab7.API.Photo
import com.example.lab7.API.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class GalleryActivityVM(application: Application) : AndroidViewModel(application) {
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            try {
                val response: Response<FlickrResponse> = RetrofitClient.flickrApiService.getRecentPhotos(apiKey = "dbe2234de5e71758f77eda26b415fe41")
                if (response.isSuccessful) {
                    _photos.value = response.body()?.photos?.photo ?: emptyList()
                }
            } catch (e: Exception) {
                Log.d("debug", "Something wrong...")
            }
        }
    }
}