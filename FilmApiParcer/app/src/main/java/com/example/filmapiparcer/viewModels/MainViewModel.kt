package com.example.filmapiparcer.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.filmapiparcer.db.AppDatabase
import com.example.filmapiparcer.db.Movie
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDao = AppDatabase.getDatabase(application).movieDao()

    val movies = MutableLiveData<List<Movie>>()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            movies.value = movieDao.getAll()
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
            loadMovies()
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            movieDao.deleteSelected()
            loadMovies()
        }
    }
}