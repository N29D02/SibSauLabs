package com.example.lab6

import androidx.activity.viewModels
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

fun NavGraph(navController: NavHostController, viewModel: MainActivityVM, modifier: Modifier, crimeDao: CrimeDao, suspectDao: SuspectDao) {

    NavHost(navController = navController, startDestination = "Criminal Intent", modifier = modifier) {
        composable(
            "Criminal Intent",
            enterTransition = {slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = {slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            MainWidget(viewModel = MainActivityVM(), navController = navController) }
        composable("Criminal Report") { CriminalReportWidget(viewModel = CriminalReportMenuVM(crimeDao = crimeDao, suspectDao = suspectDao)) }
    }
}