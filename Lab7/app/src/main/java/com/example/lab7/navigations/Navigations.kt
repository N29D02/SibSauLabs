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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lab7.API.Photo
import com.example.lab7.FavoritesScreen
import com.example.lab7.PhotoDetailScreen
import com.example.lab7.database.PhotoDao
import com.example.lab7.viewModels.GalleryActivityVM
import com.example.lab7.viewModels.SearchActivityVM
import com.example.lab7.viewModels.SettingsActivityVM
import com.example.lab7.views.GalleryScreen
import com.example.lab7.views.SearchScreen
import com.example.lab7.views.SettingsActivity

@Composable
fun NavGraph(navController: NavHostController,
             modifier: Modifier,
             galleryActivityVM: GalleryActivityVM,
             settingsActivityVM: SettingsActivityVM,
             searchActivityVM: SearchActivityVM,
             onPhotoClick: (Photo) -> Unit,
             onAddToFavorites: (Photo) -> Unit,
             photoDao: PhotoDao,
             onRemoveFromFavorites: (Photo) -> Unit
) {
    NavHost(navController = navController, startDestination = "Gallery", modifier = modifier) {
        composable(
            "Gallery",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            GalleryScreen(galleryActivityVM, onPhotoClick)
        }
        composable("Stored",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            FavoritesScreen(photoDao, onPhotoClick)
        }
        composable("Settings",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            SettingsActivity(settingsActivityVM)
        }
        composable("Search",
            enterTransition = { slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth * 2 }, animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth * 2 }, animationSpec = tween(500)) }
        ) {
            SearchScreen(searchActivityVM, onPhotoClick)
        }
        composable(
            "PhotoDetail/{photoId}",
            arguments = listOf(navArgument("photoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString("photoId")
            val photo = photoId?.let { galleryActivityVM.getPhotoById(it) }
            if (photo != null) {
                PhotoDetailScreen(photo, photoDao, onAddToFavorites, onRemoveFromFavorites)
            } else {
                Text("Photo not found")
            }
        }
    }
}