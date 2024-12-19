package com.example.to_do_list

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(taskDao: TaskDao) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(taskDao = taskDao, navController = navController)
        }
        composable("add") {
            AddScreen(taskDao = taskDao, navController = navController)
        }
    }
}