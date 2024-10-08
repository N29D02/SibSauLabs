package com.example.lab5

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityVM: ViewModel() {
    var hoursRawValue by mutableStateOf("")
    var discountValue by mutableFloatStateOf(0.1f)
}