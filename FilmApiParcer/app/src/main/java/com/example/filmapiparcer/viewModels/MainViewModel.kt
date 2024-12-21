package com.example.filmapiparcer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.filmapiparcer.db.AppDatabase
import com.example.filmapiparcer.db.Movie
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao = AppDatabase.getDatabase(application).movieDao()

    val movies: LiveData<List<Movie>> = movieDao.getAll()

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
        }
    }
}