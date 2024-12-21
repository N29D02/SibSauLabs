package com.example.filmapiparcer.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
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

    // Состояние для хранения выбранного фильма
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    // Состояние для хранения результатов поиска
    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    // Метод для установки выбранного фильма
    fun setSelectedMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    // Метод для очистки выбранного фильма
    fun clearSelectedMovie() {
        _selectedMovie.value = null
    }

    // Метод для поиска фильмов
    fun searchMovie(title: String, year: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.api.searchMovies(title, year, "b40aae5f")
                if (response.isSuccessful && response.body()?.Response == "True") {
                    val movies = response.body()?.Search ?: emptyList()
                    _searchResults.value = movies // Обновляем результаты поиска
                    _selectedMovie.value = movies.firstOrNull() // Устанавливаем первый фильм как выбранный
                } else {
                    _searchResults.value = emptyList()
                    _selectedMovie.value = null
                }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
                _selectedMovie.value = null
            }
        }
    }

    // Метод для добавления фильма в базу данных
    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
        }
    }
}