package com.example.lab6

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CriminalReportMenuVM: ViewModel() {
    var selectedImageUri by mutableStateOf<Uri?>(null)
    var title by mutableStateOf("")
    var solvedCheckState by mutableStateOf(false)
    var chosenSuspect by mutableStateOf("")
    var expandedState by mutableStateOf(false)

    
}