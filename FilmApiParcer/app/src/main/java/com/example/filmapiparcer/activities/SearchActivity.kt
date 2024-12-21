package com.example.filmapiparcer.activities

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.filmapiparcer.db.Movie
import com.example.filmapiparcer.viewModels.SearchViewModel

@Composable
fun SearchActivity(viewModel: SearchViewModel, onMovieSelected: (Movie, Boolean) -> Unit, navController: NavController) {
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    LazyColumn {
        items(searchResults) { movie ->
            //MovieItem(movie, onMovieSelected)
        }
    }

    /*
    Button(onClick = { navController.navigate(Screen.Add.route) }) {
        Text("Back to Add")
    }
    */

}