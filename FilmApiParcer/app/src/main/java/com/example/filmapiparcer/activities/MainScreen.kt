package com.example.filmapiparcer.activities

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.filmapiparcer.Navigations.CustomTopAppBar
import com.example.filmapiparcer.R
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.MainViewModel
import com.example.filmapiparcer.viewModels.MovieViewModel

@Composable
fun MovieListScreen(navController: NavController) {
    val viewModel: MovieViewModel = viewModel()
    val movies by viewModel.allMovies.collectAsState(initial = emptyList())
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    var selectedMovies by remember { mutableStateOf(emptySet<String>()) }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Movie List",
                canNavigateBack = false,
                navigateUp = {},
                onDelete = if (selectedMovies.isNotEmpty()) {
                    {
                        viewModel.deleteMoviesById(selectedMovies.toList())
                        selectedMovies = emptySet()
                    }
                } else {
                    null
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addMovie") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Movie")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (movies.isEmpty()) {
                EmptyState(
                    text = "No movies selected",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                if (isLandscape) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = paddingValues
                    ) {
                        items(movies) { movie ->
                            SelectableMovieItem(
                                movie = movie,
                                isSelected = selectedMovies.contains(movie.imdbID),
                                onSelect = { isSelected ->
                                    selectedMovies = if (isSelected) {
                                        selectedMovies + movie.imdbID
                                    } else {
                                        selectedMovies - movie.imdbID
                                    }
                                }
                            )
                        }
                    }
                } else {
                    // Отображаем фильмы в списке
                    LazyColumn(contentPadding = paddingValues) {
                        items(movies) { movie ->
                            SelectableMovieItem(
                                movie = movie,
                                isSelected = selectedMovies.contains(movie.imdbID),
                                onSelect = { isSelected ->
                                    selectedMovies = if (isSelected) {
                                        selectedMovies + movie.imdbID
                                    } else {
                                        selectedMovies - movie.imdbID
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState(text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_state_image),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun SelectableMovieItem(
    movie: Movie,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onSelect(!isSelected) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = movie.poster,
            contentDescription = "Movie Poster",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.year,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = movie.poster,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.year,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}