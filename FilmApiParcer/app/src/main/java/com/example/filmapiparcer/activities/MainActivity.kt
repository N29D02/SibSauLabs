package com.example.filmapiparcer.activities

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivity(viewModel: MainViewModel) {
    val movies by viewModel.movies.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        if (movies.isEmpty()) {
            EmptyState()
        } else {
            MovieList(movies, onMovieSelected = { movie, isSelected ->
                movie.isSelected = isSelected
                viewModel.addMovie(movie)
            })
        }
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No movies selected")
    }
}

@Composable
fun MovieList(movies: List<Movie>, onMovieSelected: (Movie, Boolean) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie, onMovieSelected)
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onMovieSelected: (Movie, Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier.size(50.dp)
        )
        Text(movie.title)
        Text(movie.year)
        Checkbox(
            checked = movie.isSelected,
            onCheckedChange = { onMovieSelected(movie, it) }
        )
    }
}