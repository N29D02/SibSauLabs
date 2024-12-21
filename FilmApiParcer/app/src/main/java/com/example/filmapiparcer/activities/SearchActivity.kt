package com.example.filmapiparcer.activities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.filmapiparcer.Navigations.CustomTopAppBar
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.MovieViewModel
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: MovieViewModel, title: String) {
    LaunchedEffect(title) {
        if (title.isNotEmpty()) {
            viewModel.searchMovie(title, "")
        }
    }

    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Search Results",
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (searchResults.isNotEmpty()) {
                LazyColumn {
                    items(searchResults) { movie ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setSelectedMovie(movie)
                                    navController.navigateUp()
                                }
                        ) {
                            MovieItem(movie)
                        }
                    }
                }
            } else {
                Text(
                    text = "No results found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}