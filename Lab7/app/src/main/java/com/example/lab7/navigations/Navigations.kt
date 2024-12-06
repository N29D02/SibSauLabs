package com.example.lab7.navigations

import android.util.Log
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lab7.viewModels.GalleryActivityVM
import com.example.lab7.viewModels.SettingsActivityVM
import com.example.lab7.views.GalleryScreen
import com.example.lab7.views.SettingsActivity

@Composable
fun NavGraph(navController: NavHostController,modifier: Modifier, galleryActivityVM: GalleryActivityVM, settingsActivityVM: SettingsActivityVM) {
    NavHost(navController = navController, startDestination = "Galery", modifier = modifier) {
        composable(
            "Galery",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            GalleryScreen(galleryActivityVM)
        }
        composable("Stored") {

        }
        composable("Settings",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            SettingsActivity(settingsActivityVM)
        }
        composable("Search") {

        }
    }
}