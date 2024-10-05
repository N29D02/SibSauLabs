package com.example.lab6

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CriminalReportMenuVM(private val crimeDao: CrimeDao, suspectDao: SuspectDao): ViewModel() {
    var selectedImageUri by mutableStateOf<Uri?>(null)
    var title by mutableStateOf("")
    var solvedCheckState by mutableStateOf(false)
    var suspectList: LiveData<List<Suspect>> = suspectDao.all
    var chosenSuspect by mutableStateOf("")
    var expandedState by mutableStateOf(false)

    fun addCrime(title: String, selectedImageUri: Uri, solvedCheckState: Boolean, chosenSuspect: String, expandedState: Boolean){
        val crime = Crime(title = title, uri = selectedImageUri.toString(), isSolved = solvedCheckState, date = "")
        crimeDao.insert(crime)
    }
}