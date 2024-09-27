package com.example.lab6

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable

fun NavGraph(navController: NavHostController, viewModel: MainActivityVM) {
    NavHost(navController = navController, startDestination = "main") {
        composable(
            "main",
            enterTransition = {slideInHorizontally(animationSpec = tween(500)) },
            exitTransition = {slideOutHorizontally(animationSpec = tween(500)) }
        ) { MainWidget(viewModel = viewModel, navController = navController) }
        composable("criminalReport") { CriminalReportWidget() }
    }
}