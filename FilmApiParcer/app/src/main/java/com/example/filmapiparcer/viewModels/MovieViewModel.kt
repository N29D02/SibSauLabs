package com.example.filmapiparcer.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.filmapiparcer.db.AppDatabase
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val movieDao = AppDatabase.getDatabase(application).movieDao()
    val allMovies = movieDao.getAll().asFlow().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _searchedMovie = MutableStateFlow<Movie?>(null)
    val searchedMovie: StateFlow<Movie?> = _searchedMovie

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    fun searchMovie(title: String, year: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.searchMovies(title, year, "b40aae5f")
                if (response.isSuccessful && response.body()?.Response == "True") {
                    val movies = response.body()?.Search ?: emptyList()
                    _searchedMovie.value = movies.firstOrNull()
                    _searchResults.value = movies
                } else {
                    _searchedMovie.value = null
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _searchedMovie.value = null
                _searchResults.value = emptyList()
            }
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
        }
    }
}