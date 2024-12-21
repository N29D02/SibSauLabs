package com.example.filmapiparcer.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val searchResults = MutableLiveData<List<Movie>>()

    fun searchMovies(query: String, year: String? = null) {
        viewModelScope.launch {
            val response = RetrofitClient.api.searchMovies(query, year, "b40aae5f")
            if (response.isSuccessful) {
                searchResults.value = response.body()?.Search
            }
        }
    }
}