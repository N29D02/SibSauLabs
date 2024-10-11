package com.example.lab6

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CriminalReportMenuVM(application: Application) : AndroidViewModel(application) {
    private val suspectDao = AppDatabase.getDatabase(application).suspectDao()

    var selectedImageUri by mutableStateOf<Uri?>(null)
    var solvedCheckState by mutableStateOf(false)
    var expandedState by mutableStateOf(false)
    var chosenSuspect by mutableStateOf("")
    val suspects: LiveData<List<Suspect>> = MutableLiveData(emptyList())

    init {
        viewModelScope.launch {
            (suspects as MutableLiveData).value = suspectDao.getAllSuspects()
        }
    }
}