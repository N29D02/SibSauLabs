package com.example.lab6

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable

fun NavGraph(navController: NavHostController, viewModel: MainActivityVM, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "main", modifier = modifier) {
        composable(
            "main",
            enterTransition = {slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = {slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) { MainWidget(viewModel = viewModel, navController = navController) }
        composable("criminalReport") { CriminalReportWidget() }
    }
}