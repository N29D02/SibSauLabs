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

class SettingsActivityVM(application: Application) : AndroidViewModel(application) {
    private val _useNotification = MutableStateFlow(false)
    val useNotification: StateFlow<Boolean> = _useNotification

    fun settingNotifications() {
        viewModelScope.launch {
            _useNotification.value = !_useNotification.value
        }
    }
}