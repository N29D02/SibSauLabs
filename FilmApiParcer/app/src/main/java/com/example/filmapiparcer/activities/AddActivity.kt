package com.example.filmapiparcer.activities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.filmapiparcer.Navigations.CustomTopAppBar
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.MainViewModel
import com.example.filmapiparcer.viewModels.MovieViewModel

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

@Composable
fun AddMovieScreen(navController: NavController, viewModel: MovieViewModel) {
    val selectedMovie by viewModel.selectedMovie.collectAsState(initial = null)

    var title by remember { mutableStateOf(selectedMovie?.title ?: "") }
    var year by remember { mutableStateOf(selectedMovie?.year ?: "") }

    val context = LocalContext.current

    LaunchedEffect(selectedMovie) {
        if (selectedMovie != null) {
            title = selectedMovie!!.title
            year = selectedMovie!!.year
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Add Movie",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Movie Title") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        navController.navigate("search?title=${title}")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (isNetworkAvailable(context)) {
                    viewModel.searchMovie(title, year) // Вызываем поиск фильмов
                } else {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            selectedMovie?.let { movie ->
                MovieItem(movie)
                Button(
                    onClick = {
                        viewModel.addMovie(movie) // Добавляем фильм в базу данных
                        viewModel.clearSelectedMovie() // Очищаем выбранный фильм
                        navController.navigateUp() // Возвращаемся на предыдущий экран
                    },
                    enabled = true
                ) {
                    Text("Add Movie")
                }
            }
        }
    }
}