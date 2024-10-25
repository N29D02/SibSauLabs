package com.example.lab6

import android.app.Application
import android.net.Uri
import android.util.Log
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CriminalReportMenuVM(application: Application) : AndroidViewModel(application) {
    private val crimeDao = AppDatabase.getDatabase(application).crimeDao()
    private val suspectDao = AppDatabase.getDatabase(application).suspectDao()

    var selectedImageUri by mutableStateOf<Uri?>(null)
    var solvedCheckState by mutableStateOf(false)
    var expandedState by mutableStateOf(false)
    var chosenSuspect by mutableStateOf("")
    var selectedDate by mutableStateOf("")
    var showDatePicker by mutableStateOf(false)
    var title by mutableStateOf("")
    val suspectsLiveData: LiveData<List<Suspect>> = suspectDao.getAllSuspectsLiveData()
    var suspects: List<Suspect> = emptyList()

    init{
        viewModelScope.launch {

            suspects = suspectDao.getAllSuspects()
            if (!containsSuspect(suspects, "John", "Doe"))
                suspectDao.insertSuspect(Suspect(firstName = "John", lastName = "Doe"))
            if (!containsSuspect(suspects, "Jane", "Smith"))
                suspectDao.insertSuspect(Suspect(firstName = "Jane", lastName = "Smith"))
            if (!containsSuspect(suspects, "Alice", "Johnson"))
                suspectDao.insertSuspect(Suspect(firstName = "Alice", lastName = "Johnson"))
        }
    }

    fun containsSuspect(suspects: List<Suspect>, firstName: String, lastName: String): Boolean {
        return suspects.any { it.firstName == firstName && it.lastName == lastName }
    }

    fun addCrime() {
        val uriString = selectedImageUri?.toString()
        viewModelScope.launch {
            crimeDao.insertCrime(Crime(title = title, uri = uriString, date = selectedDate, isSolved = solvedCheckState, suspect_name = chosenSuspect))
            setOnDefaultModel()
        }
    }

    fun setSelectedDate(date: Date) {
        selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
    }

    fun setOnDefaultModel(){
        solvedCheckState = false
        selectedImageUri = null
        expandedState = false
        chosenSuspect = ""
        selectedDate = ""
        showDatePicker = false
        title = ""
    }
}