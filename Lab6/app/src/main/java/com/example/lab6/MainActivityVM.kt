package com.example.lab6

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class MainActivityVM(application: Application): AndroidViewModel(application) {
    var screenTitle by mutableStateOf("Criminal Intent")

    private val suspectDao = AppDatabase.getDatabase(application).suspectDao()
    private val crimeDao = AppDatabase.getDatabase(application).crimeDao()

    val crimes: LiveData<List<Crime>> = crimeDao.getAllCrimesLiveData()

    init {
        viewModelScope.launch {

        }
    }

    fun deleteCrime(crime: Crime) {
        viewModelScope.launch {
            crimeDao.deleteCrimeById(crime.id)
        }
    }


}