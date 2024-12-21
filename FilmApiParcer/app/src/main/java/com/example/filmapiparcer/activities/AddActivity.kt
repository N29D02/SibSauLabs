package com.example.filmapiparcer.activities

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.filmapiparcer.Navigations.Screen
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.MainViewModel
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun AddActivity(viewModel: SearchViewModel, onMovieSelected: (Movie, Boolean) -> Unit, navController: NavController) {
    var query by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search") }
        )
        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") }
        )
        Button(onClick = { viewModel.searchMovies(query, year) }) {
            Text("Search")
        }

        if (searchResults.isNotEmpty()) {
            SearchResults(searchResults, onMovieSelected)
        }

        Button(onClick = { navController.navigate(Screen.Main.route) }) {
            Text("Back to Main")
        }
    }
}

@Composable
fun SearchResults(movies: List<Movie>, onMovieSelected: (Movie, Boolean) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie, onMovieSelected)
        }
    }
}