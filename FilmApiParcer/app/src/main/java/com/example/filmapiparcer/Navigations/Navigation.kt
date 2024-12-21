package com.example.filmapiparcer.Navigations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmapiparcer.activities.AddMovieScreen
import com.example.filmapiparcer.activities.MovieListScreen
import com.example.filmapiparcer.activities.SearchScreen
import com.example.filmapiparcer.viewModels.MovieViewModel
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(navController)
        }
        composable("addMovie") {
            AddMovieScreen(navController, movieViewModel)
        }
        composable(
            "search?title={title}",
            arguments = listOf(navArgument("title") { defaultValue = "" })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            SearchScreen(navController, movieViewModel, title)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            } else {
                null
            }
        },
        actions = {
            if (onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = Color.White,
                        contentDescription = "Delete"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}