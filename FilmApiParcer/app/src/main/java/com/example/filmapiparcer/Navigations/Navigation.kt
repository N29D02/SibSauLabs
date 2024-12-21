package com.example.filmapiparcer.Navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.filmapiparcer.activities.AddActivity
import com.example.filmapiparcer.activities.MainScreen
import com.example.filmapiparcer.activities.SearchActivity
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun SetupNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Add.route) {
            AddActivity(
                viewModel = SearchViewModel(),
                onMovieSelected = { movie, isSelected ->
                    
                },
                navController = navController
            )
        }
        composable(Screen.Search.route) {
            SearchActivity(
                viewModel = SearchViewModel(),
                onMovieSelected = { movie, isSelected ->

                },
                navController = navController
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Add : Screen("add")
    object Search : Screen("search")
}