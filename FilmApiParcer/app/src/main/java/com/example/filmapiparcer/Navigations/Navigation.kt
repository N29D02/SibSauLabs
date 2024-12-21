package com.example.filmapiparcer.Navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmapiparcer.activities.AddMovieScreen
import com.example.filmapiparcer.activities.MovieListScreen
import com.example.filmapiparcer.activities.SearchActivity
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun MovieApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movieList") {
        composable("movieList") {
            MovieListScreen(navController)
        }
        composable("addMovie") {
            AddMovieScreen(navController)
        }
    }
}