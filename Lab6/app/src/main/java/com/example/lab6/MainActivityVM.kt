package com.example.lab6

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class MainActivityVM: ViewModel() {
    var screenTitle by mutableStateOf("Criminal Intent")
}